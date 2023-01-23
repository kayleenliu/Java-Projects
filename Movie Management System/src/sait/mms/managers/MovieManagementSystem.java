package sait.mms.managers;

import sait.mms.drivers.MariaDBDriver;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;


/**
 * Used to manage the movies on the database
 * @author Scott Normore, Gao Liu, Christian Lay, Kin Shing Chong
 */
public class MovieManagementSystem {
	MariaDBDriver database = new MariaDBDriver();
	Scanner input = new Scanner(System.in);
	/**
	 * Used to display the main menu
	 */
	public void displayMenu() {
		while(true) {
			System.out.println("Jim's Movie Manager");	
			System.out.printf("%-6d%-30s%n",1,"Add new movie");
			System.out.printf("%-6d%-30s%n",2,"Print movies released in year");
			System.out.printf("%-6d%-30s%n",3,"Print random list of movies");
			System.out.printf("%-6d%-30s%n",4,"Delete a movie");
			System.out.printf("%-6d%-30s%n",5,"Exit");
			System.out.print("\nEnter an option: ");
			String userInput = input.next();
			System.out.println("");
			if(userInput.equals("1")) {
				addMovie();
			}else if(userInput.equals("4")) {
				deleteMovie();
			}else if(userInput.equals("2")) {
				printMoviesInYear();
			}else if(userInput.equals("3")) {
				printRandomMovies();
			}else if(userInput.equals("5")) {
				System.out.println("\nGoodbye!\n");
				System.exit(0);
			}else {
				System.out.println("Invalid Input, please try again!");
				System.out.println();
			}
		}
	}
	/**
	 * Used to add a movie
	 */
	public void addMovie() {
		try {
			database.connect();
			System.out.print("Enter movie title: ");
			input.nextLine();
			String title = input.nextLine();
			if(title.length()>255) {
				throw new InputMismatchException();
			}
			System.out.print("Enter duration: ");
			int duration = input.nextInt();
			if(duration<=0) {
				throw new InputMismatchException();
			}
			System.out.print("Enter year: ");
			int year = input.nextInt();
			if(year<=0) {
				throw new InputMismatchException();
			}
			String sqlStmt = "INSERT INTO movies (duration, title, year) VALUES ('" + duration +"','" + title  + "','" + year + "') ";
			database.update(sqlStmt);
			database.disconnect();
			System.out.println("Added movie to database\n");
		} catch (InputMismatchException e) {
			System.out.println("Invalid input, please try again! \n");
			input.nextLine();
		}catch(SQLDataException e) {
			System.out.println("Invalid input, please try again! \n");
		}
		catch (SQLException e){
			System.out.println("Unknown Error Occured\n");
		}
	}
	/**
	 * Used to display movies in a given year
	 */
	public void printMoviesInYear() {
		try {
			database.connect();
			System.out.print("Enter In Year: ");
			int year = input.nextInt();
			
			ResultSet rowCount = database.get("SELECT COUNT(*) FROM movies WHERE year = "+year);
			rowCount.next();
			if(rowCount.getInt("COUNT(*)")==0) {
				throw new SQLDataException();
			}
			ResultSet data = database.get("SELECT * FROM movies WHERE year = "+year);
			int totalDuration = 0;
			System.out.println("\nMovie List");
			System.out.printf("%-15s%-8s%-55s%n","Duration","Year","Title");
			while(data.next()) {
				System.out.printf("%-15d%-8d%-55s%n",data.getInt("duration"),data.getInt("year"),data.getString("title"));
				totalDuration += data.getInt("duration");
			}
			System.out.println("\nTotal duration: "+totalDuration +" minutes\n\n");
			database.disconnect();
		}catch (InputMismatchException e) {
			System.out.println("Invalid input, please try again! \n");
			input.nextLine();
		}catch(SQLDataException e) {
			System.out.println("No movies in given year, please try again! \n");
		}
		catch(SQLException e) {
			System.out.println("Unknown Error Occured\n");
		}
	}
	/**
	 * Used to print a random subset of movies from a given number
	 */
	public void printRandomMovies() {
		try {
			database.connect();
			System.out.print("Enter Number of Movies: ");
			int number = input.nextInt();
			ResultSet rowCount = database.get("SELECT COUNT(*) FROM movies");
			rowCount.next();
			if(rowCount.getInt("COUNT(*)")<number) {
				throw new SQLDataException();
			}else if(number<=0) {
				throw new InputMismatchException();
			}
			ResultSet data = database.get("SELECT * FROM movies ORDER BY RAND() LIMIT "+number);
			int totalDuration = 0;
			System.out.println("\nMovie List");
			System.out.printf("%-15s%-8s%-55s%n","Duration","Year","Title");
			while(data.next()) {
				System.out.printf("%-15d%-8d%-55s%n",data.getInt("duration"),data.getInt("year"),data.getString("title"));
				totalDuration += data.getInt("duration");
			}
			System.out.println("\nTotal duration: "+totalDuration +" minutes\n\n");
			database.disconnect();
		}catch (InputMismatchException e) {
			System.out.println("Invalid input, please try again! \n");
			input.nextLine();
		}catch(SQLDataException e) {
			System.out.println("Too many movies! Please try again \n");
		}
		catch(SQLException e) {
			System.out.println("Unknown Error Occured\n");
		}
	}
	/**
	 * Used to delete movies from database
	 */
	public void deleteMovie() {
		try {
			database.connect();
			System.out.print("Enter the movie ID that you want to delete: ");
			int id = input.nextInt();
			String sqlStmt = "DELETE FROM movies WHERE ID = " + id;
			int rows = database.update(sqlStmt);
			if(rows==0) {
				throw new SQLDataException();
			}
			database.disconnect();
			System.out.println("Movie "+ id +" is deleted!\n");
		}catch (InputMismatchException e) {
			System.out.println("Invalid input, please try again! \n");
			input.nextLine();
		}
		catch(SQLDataException e) {
			System.out.println("No such movie, please try again! \n");
		}
		catch (SQLException e){
			System.out.println("Unknown Error Occured\n");
		}
	}

}
