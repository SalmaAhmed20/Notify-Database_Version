package comm.API;

import comm.DatabaseSim.TemplateRepo;
import comm.Model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
public class TemplateController {
	private final TemplateRepo TDB;

	@Autowired
	public TemplateController(@Qualifier("mySql") TemplateRepo tdb) {
		TDB = tdb;

	}

	@RequestMapping(method = RequestMethod.POST, value = "API/Template")
	public boolean addTemplate(@NonNull @RequestBody Template Temp) {
		return TDB.create(Temp.getSubject(), Temp.getContent(), Temp.getSupportedLanguages(),
				Temp.getNumPlaceHolders());
	}

	@RequestMapping(method = RequestMethod.GET, value = "API/Template")
	public ArrayList<Template> getAllTemp() {
		return TDB.getCreatedTemplates();
	}

	@RequestMapping(method = RequestMethod.GET, value = "API/Template/{id}")
	public Template getTemplates(@PathVariable("id") int id) {
		return TDB.Read(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "API/Template/{id}")
	public boolean deleteTemplate(@PathVariable("id") int id) {
		return TDB.delete(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "API/Template/{id}")
	public boolean UpdateTemp(@RequestBody Template t, @PathVariable("id") int id) {
		return TDB.Update(t, id);
	}
}
