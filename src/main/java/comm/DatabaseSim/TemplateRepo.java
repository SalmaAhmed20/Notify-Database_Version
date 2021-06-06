package comm.DatabaseSim;

import comm.Model.Languages;
import comm.Model.Template;

import java.util.ArrayList;

public interface TemplateRepo {
    public boolean create( String sub, String con, Languages lang, int numPlaceHold);
    public ArrayList <Template> getCreatedTemplates();
    public Template Read( int ID);

    public boolean delete(int ID);

    public boolean Update(Template t,int id);
}
