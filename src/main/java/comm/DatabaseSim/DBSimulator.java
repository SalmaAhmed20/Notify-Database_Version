package comm.DatabaseSim;


import comm.Model.Languages;
import comm.Model.Template;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository("TDB")
public class DBSimulator implements TemplateRepo {

    private static ArrayList<Template> createdTemplates = new ArrayList <>();

    public ArrayList<Template> getCreatedTemplates() {
        return createdTemplates;
    }

    @Override
    public boolean create( String sub, String content, Languages lang, int numPlaceHold ) {
        Template newTmp = new Template(sub,content,lang,numPlaceHold);
        newTmp.setID(createdTemplates.size());
        return createdTemplates.add(newTmp);
    }

    @Override
    public Template Read( int ID ) {
       
        return null;
    }

    @Override
    public boolean delete( int ID ) {
        if (!createdTemplates.isEmpty()) {
            Template t = null;
            for (Template createdTemplate : createdTemplates) {
                if ( createdTemplate.getID() == ID ) {
                    t = createdTemplate;
                    break;
                }
            }
            if (t != null)
                return createdTemplates.remove(t);
        }
        return false;

    }

    @Override
    public boolean Update( Template t,int id) {
        for (int i =0;i<createdTemplates.size();i++)
        {
            if (createdTemplates.get(i).getID()==id) {
                t.setID(id);
                if (t.getSubject()==null)
                    t.setSubject(createdTemplates.get(i).getSubject());
                if ( t.getContent()==null)
                    t.setContent(createdTemplates.get(i).getContent());
                /*if (t.getNumPlaceHolders()==0)
                    t.setNumPlaceHolders(createdTemplates.get(i).getNumPlaceHolders());*/
                if ( t.getSupportedLanguages()==null )
                    t.setSupportedLanguages(createdTemplates.get(i).getSupportedLanguages());
                createdTemplates.set(i , t);
                return true;
            }
        }
        return false;
    }
}
