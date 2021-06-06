package comm.DatabaseSim;

import comm.Model.Languages;
import comm.Model.Template;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository("mySql")
public class SqlDB implements TemplateRepo {
	private DBconnection myDB=new SqlConnection();

	@Override
	public boolean create(String sub, String con, Languages lang, int numPlaceHold) {
		try {
			myDB.getStatement().executeUpdate("INSERT INTO template (Subjet,Content,Language,numofPlace) VALUES(" + "'" + sub + "',"
					+ "'" + con + "'," + "'" + lang.toString() + "'," + "'" + numPlaceHold + "')");
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}

	}

	@Override
	public ArrayList<Template> getCreatedTemplates() {
		ArrayList<Template> a = new ArrayList<>();
		try {
			var resultSet = myDB.getStatement().executeQuery("SELECT * FROM template");
			while (resultSet.next()) {
				Languages l = Languages.valueOf(resultSet.getString("Language"));
				Template t = new Template(resultSet.getString("Subjet"), resultSet.getString("Content")
						, l,resultSet.getInt("numofPlace"));
				t.setID(resultSet.getInt("ID"));
				a.add(t);
			}
			return a;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}

	}

	@Override
	public Template Read(int ID) {
		try {
			var resultSet = myDB.getStatement().executeQuery("SELECT * FROM template WHERE ID = " + "'" + ID + "';");
			if (resultSet.next()) {
				Languages l = Languages.valueOf(resultSet.getString("Language"));
				Template t = new Template(resultSet.getString("Subjet"), resultSet.getString("Content"), l,
						resultSet.getInt("numofPlace"));
				t.setID(resultSet.getInt("ID"));
				return t;
			} else
				return null;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(int ID) {
		try {
			myDB.getStatement().executeUpdate("DELETE FROM template WHERE ID = " + "'" + ID + "';");
			return true;
		} catch (SQLException throwables) {

			throwables.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean Update(Template t, int id) {
		try {
			//
			String query = "UPDATE template SET" + ((t.getSubject()!=null)?" Subjet = " + "'" + t.getSubject() +"' ,": "" ) 
					+ ((t.getContent()!=null)?(" Content = " + "'" + t.getContent() + "' ,") :"" )+
					((t.getSupportedLanguages()!=null)?(" Language = " + "'" + t.getSupportedLanguages().toString()+ "' ,"):"")
					 +((t.getNumPlaceHolders()!=1)?( " numofPlace = " + "'" + t.getNumPlaceHolders())+"'":"");
				if(query.lastIndexOf(',')>query.length()-3){ 
				var builder =new StringBuilder(query).deleteCharAt(query.lastIndexOf(','));
				query = builder.toString();
				}
				query+= " Where ID =" + "'" + id + "';";
			if (t != null) {
				myDB.getStatement().executeUpdate(query);
				return true;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}
}
