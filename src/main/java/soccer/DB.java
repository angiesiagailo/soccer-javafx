package soccer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import soccer.model.Coach;
import soccer.model.League;

public class DB {
	private Connection conn = null;
	private static DB db = new DB();

	private DB() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			Properties connectionProps = new Properties();
			connectionProps.put("user", prop.getProperty("db.user"));
			connectionProps.put("password", prop.getProperty("db.password"));

			String serverName = prop.getProperty("db.host");
			String port = prop.getProperty("db.port");
			String db = prop.getProperty("db.instance");

			conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/" + db, connectionProps);

			System.out.println("Connected to database");
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	/** Returns an array list of the leagues in the database. */
	public static ArrayList<League> loadLeagues() {
		ArrayList<League> list = new ArrayList<>();
		String queryString = " select league.league_id, league_name, count(team.league_id)  as teams_count " +
				" from league  " +
				" left join team on league.league_id = team.league_id " +
				" group by league.league_id, league_name " +
				" order by league_name ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
				ResultSet rs = queryStmt.executeQuery();) {

			while (rs.next()) {
				int leagueId = rs.getInt("league_id");
				String leagueName = rs.getString("league_name");

				boolean hasTeams = false;
				if (rs.getInt("teams_count") > 0) {
					hasTeams = true;
				}

				League league = new League(leagueId, leagueName, hasTeams);

				list.add(league);
			}

		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return list;
	}

	/** Loads a single league given an id. */
	public static League loadLeague(int leagueId) {
		String queryString = " select league.league_id, league_name " +
				" from league  " +
				" where league_id = ? ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString)) {
			queryStmt.setInt(1, leagueId);

			try (ResultSet rs = queryStmt.executeQuery()) {

				if (rs.next()) {
					String leagueName = rs.getString("league_name");

					return new League(leagueId, leagueName, false);
				}
			}
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return null;
	}

	/** Adds a new league to the database. */
	public static void insertLeague(String leagueName) {
		String query = "insert into league(league_name) values (?)";

		try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

			insertStmt.setString(1, leagueName);
			insertStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	/** Updates the name of a league in the database. */
	public static void updateLeague(League league) {
		String query = "update league set league_name = ? where league_id = ?";

		try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

			updateStmt.setString(1, league.getLeagueName());
			updateStmt.setInt(2, league.getLeagueId());

			updateStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	/** Deletes the given league from the database. */
	public static void deleteLeague(int leagueId) {
		String query = "delete from league where league_id = ?";

		try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

			updateStmt.setInt(1, leagueId);
			updateStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}
	
	public static ArrayList<Coach> loadCoaches() {
    	ArrayList<Coach> list = new ArrayList<>();
    	String queryString = "SELECT coach_id, coach_first_name, coach_last_name, coach_phone_nbr, coach_email FROM coach";

    	try (
            	PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
            	ResultSet rs = queryStmt.executeQuery();) {

        	while (rs.next()) {
            	int coachId = rs.getInt("coach_id");
            	String firstName = rs.getString("coach_first_name");
            	String lastName = rs.getString("coach_last_name");
            	String phone = rs.getString("coach_phone_nbr");
            	String email = rs.getString("coach_email");

            	Coach coach = new Coach(coachId, firstName, lastName, phone, email);
            	list.add(coach);
        	}
		} catch (Exception ex) {
        	System.err.println(ex);
        	ex.printStackTrace(System.err);
		}
    	return list;
	}

	public static Coach loadCoach(int coachId) {
    	String queryString = "SELECT coach_first_name, coach_last_name, coach_phone_nbr, coach_email FROM coach WHERE coach_id = ?";

		try (
            PreparedStatement queryStmt = db.conn.prepareStatement(queryString)) {
        	queryStmt.setInt(1, coachId);

        		try (ResultSet rs = queryStmt.executeQuery()) {

            		if (rs.next()) {
               			String firstName = rs.getString("coach_first_name");
               			String lastName = rs.getString("coach_last_name");
                		String phone = rs.getString("coach_phone_nbr");
               			String email = rs.getString("coach_email");

                		return new Coach(coachId, firstName, lastName, phone, email);
            		}
        		}
   		} catch (Exception ex) {
        	System.err.println(ex);
        	ex.printStackTrace(System.err);
		}

    return null;
	}

	public static void insertCoach(String firstName, String lastName, String phone, String email) {
    	String query = "INSERT INTO coach(coach_first_name, coach_last_name, coach_phone_nbr, coach_email) VALUES (?, ?, ?, ?)";

    	try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

    		insertStmt.setString(1, firstName);
        	insertStmt.setString(2, lastName);
        	insertStmt.setString(3, phone);
        	insertStmt.setString(4, email);

        	insertStmt.executeUpdate();
    	} catch (Exception ex) {
        	System.err.println(ex);
        	ex.printStackTrace(System.err);
    	}
	}

public static void updateCoach(Coach coach) {
    String query = "UPDATE coach SET coach_first_name = ?, coach_last_name = ?, coach_phone_nbr = ?, coach_email = ? WHERE coach_id = ?";

    try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

        updateStmt.setString(1, coach.getFirstName());
        updateStmt.setString(2, coach.getLastName());
        updateStmt.setString(3, coach.getPhone());
        updateStmt.setString(4, coach.getEmail());
        updateStmt.setInt(5, coach.getCoachId());

        updateStmt.executeUpdate();
    } catch (Exception ex) {
        System.err.println(ex);
        ex.printStackTrace(System.err);
    }
}

public static void deleteCoach(int coachId) {
    String query = "DELETE FROM coach WHERE coach_id = ?";

    try (PreparedStatement deleteStmt = db.conn.prepareStatement(query)) {

        deleteStmt.setInt(1, coachId);
        deleteStmt.executeUpdate();
    } catch (Exception ex) {
        System.err.println(ex);
        ex.printStackTrace(System.err);
    }
}
}