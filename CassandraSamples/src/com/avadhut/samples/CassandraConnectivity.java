package com.avadhut.samples;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.datastax.driver.core.exceptions.SyntaxError;

public class CassandraConnectivity {

	private static final String KEYSPACE = "mykeyspace";

	public static void main(String[] args) {

		// Create the builder
		Cluster.Builder myBuilder = Cluster.builder().addContactPoint("127.0.0.1");

		// Creating cluster
		Cluster cluster = myBuilder.build();

		// Create/connect to session
		Session session = cluster.connect();

		// Create query to create keyspace
		String createkeySpaceQuery = "CREATE KEYSPACE " + KEYSPACE + " WITH replication "
				+ "= {'class':'SimpleStrategy','replication_factor':3}; ";

		try {
			session.execute(createkeySpaceQuery);
			System.out.println("Creating KEYSPACE " + KEYSPACE + "..");
			System.out.println("\nQUERY=>" + createkeySpaceQuery);
		} catch (AlreadyExistsException e) {
			System.err.println(e.equals(e.getMessage()));
		}

		// create query to alter the keyspace
		String alterKeySpaceQuery = "ALTER KEYSPACE " + KEYSPACE + " WITH replication = {'class': 'SimpleStrategy',"
				+ "'replication_factor' : '" + 1 + "'};";

		try {
			session.execute(alterKeySpaceQuery);
			System.out.println("\nAltering KEYSPACE " + KEYSPACE + "..");
			System.out.println("\nQUERY=>" + alterKeySpaceQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		session.execute("USE " + KEYSPACE + ";");

		// create Query to create table in keyspace
		String createTableQuery = "CREATE TABLE employee(emp_id int PRIMARY KEY, emp_name text);";
		try {
			session.execute(createTableQuery);
			System.out.println("\ncreateing table..");
			System.out.println("\nQUERY=>" + createTableQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to insert data in table
		String insertTableQuery = "INSERT INTO employee (emp_id , emp_name ) VALUES ( 1, 'avadhut') ;";
		String insertTableQuery2 = "INSERT INTO employee (emp_id , emp_name ) VALUES ( 2, 'redhat') ;";
		try {
			session.execute(insertTableQuery);
			session.execute(insertTableQuery2);
			System.out.println("\ninserting data in table..");
			System.out.println("\nQUERY=>" + insertTableQuery);
			System.out.println("\nQUERY=>" + insertTableQuery2);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to read data from table
		String readTableQuery = "SELECT * FROM employee;";
		try {
			System.out.println("\nreading data from table..");
			System.out.println("\nQUERY=>" + readTableQuery);
			System.out.println(session.execute(readTableQuery).all());
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to alter table
		String alterTableQuery = "ALTER TABLE employee ADD emp_city text";
		try {
			session.execute(alterTableQuery);
			System.out.println("\naltering table..emp_city column added");
			System.out.println("\nQUERY=>" + alterTableQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to update data in table
		String updateTableQuery = "UPDATE employee SET emp_name='abhijit'  WHERE emp_id=2;";
		try {
			session.execute(updateTableQuery);
			System.out.println("\nupdating data in table..");
			System.out.println("\nQUERY=>" + updateTableQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to update data in table
		String deleteQuery = "DELETE emp_name FROM employee WHERE emp_id=2;";
		try {
			session.execute(deleteQuery);
			System.out.println("\ndeleting data in table..");
			System.out.println("\nQUERY=>" + deleteQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to alter table
		String alterTableQuery2 = "ALTER TABLE employee DROP emp_city;";
		try {
			session.execute(alterTableQuery2);
			System.out.println("\naltering table..emp_city column deleted");
			System.out.println("\nQUERY=>" + alterTableQuery2);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		try {
			ResultSet result = session.execute(readTableQuery);
			System.out.println("\nreading data from table..");
			System.out.println("\nQUERY=>" + readTableQuery);
			System.out.println(result.all());
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to create index on table column
		String createIndexQuery = "CREATE INDEX name ON employee(emp_name);";
		try {
			session.execute(createIndexQuery);
			System.out.println("\ncreating index on table column");
			System.out.println("\nQUERY=>" + createIndexQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to drop index on table column
		String dropIndexQuery = "DROP INDEX name;";
		try {
			session.execute(dropIndexQuery);
			System.out.println("\ndeleting index on table column");
			System.out.println("\nQUERY=>" + dropIndexQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to truncate table
		String truncateTableQuery = "TRUNCATE employee;";
		try {
			session.execute(truncateTableQuery);
			System.out.println("\ntruncating table..");
			System.out.println("\nQUERY=>" + truncateTableQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to drop table in keyspace
		String dropTableQuery = "DROP TABLE employee;";
		try {
			session.execute(dropTableQuery);
			System.out.println("\ndeleting table..");
			System.out.println("\nQUERY=>" + dropTableQuery);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		System.out.println("----------------------------------------------------------------------------------------------------------");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		String createTableQueryCollection = "CREATE TABLE contracts (id int PRIMARY KEY, name text, phone set<varint>, email list<text>,  address map<text, text>);";
		try {
			session.execute(createTableQueryCollection);
			System.out.println("\ncreateing table for collection illustration..");
			System.out.println("\nQUERY=>" + createTableQueryCollection);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to insert data in table
		String insertTableQueryCollection1 = "INSERT INTO contracts (id, name, phone, email, address) VALUES (1, 'avadhut',  {9848022338,9848022339}  , ['avadhut@gmail.com','avadhut@yahoo.com'], {'home' : 'pune' , 'office' : 'mumbai' } );";
		String insertTableQueryCollection2 = " INSERT INTO contracts (id, name, phone, email, address) VALUES (2, 'abhijit',  {9848022345,9842345639} , ['abhijit@gmail.com','abhijit@yahoo.com'], {'home' : 'mumbai' , 'office' : 'delhi' } );";
		try {
			session.execute(insertTableQueryCollection1);
			session.execute(insertTableQueryCollection2);
			System.out.println("\ninserting data in table..");
			System.out.println("\nQUERY=>" + insertTableQueryCollection1);
			System.out.println("\nQUERY=>" + insertTableQueryCollection2);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to read data from table
		String readTableQueryCollection = "SELECT * FROM contracts;";
		try {
			System.out.println("\nreading data from table..");
			System.out.println("\nQUERY=>" + readTableQueryCollection);
			System.out.println(session.execute(readTableQueryCollection).all());
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		// create Query to update data in table
		String updateTableQueryCollection = "UPDATE contracts SET phone = phone + {9848022330}, email=email + ['avadhut@hotmail.com'] where id=1;";
		try {
			session.execute(updateTableQueryCollection);
			System.out.println("\nupdating data in table..");
			System.out.println("\nQUERY=>" + updateTableQueryCollection);
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		try {
			System.out.println("\nreading data from table..");
			System.out.println("\nQUERY=>" + readTableQueryCollection);
			System.out.println(session.execute(readTableQueryCollection).all());
		} catch (SyntaxError e) {
			System.err.println(e.getMessage());
		}

		System.out.println("----------------------------------------------------------------------------------------------------------");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// create query to drop the keyspace
		String dropKeySpaceQuery = "DROP KEYSPACE " + KEYSPACE + " ;";
		try {
			session.execute(dropKeySpaceQuery);

			System.out.println("\nDropping KEYSPACE " + KEYSPACE);
			System.out.println("\nQUERY=>" + dropKeySpaceQuery);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}


	}

}
