package uk.ac.city.logmap.melt;

import de.uni_mannheim.informatik.dws.melt.matching_base.MatcherURL;
import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.Parameters;
import uk.ac.ox.krr.logmap2.io.OAEIAlignmentOutput;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LogMap matcher packaging with MELT extending MatcherURL
 * @author ernesto
 *
 */
public class LogMap_MELT extends MatcherURL {  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogMap_MELT.class);

	@Override
	public URL match(URL source, URL target, URL inputAlignment)
			throws Exception {
		
	    //Accessing the resources:
	    //   - all files in "oaei-resources" folder are stored in the current working directory and can be accessed with 
	    //        Files.readAllLines(Paths.get("oaei-resources", "configuration_oaei.txt"));
	    //   - all files in "src/main/resources" folder are compiled to the resulting jar and can be accessed with
	    //         getClass().getClassLoader().getResourceAsStream("configuration_jar.txt");	      
		
		
		try {
			
			LogMap2_Matcher logmap = new LogMap2_Matcher(source.toURI().toString(), target.toURI().toString(), "oaei-resources/");
			
			LOGGER.info("Matching with LogMap completed");			
			
			OAEIAlignmentOutput alignment_output = new OAEIAlignmentOutput("alignment", logmap.getIRIOntology1(), logmap.getIRIOntology2());
			
			
			for (MappingObjectStr mapping : logmap.getLogmap2_Mappings()) {
				
				if (ignoreMapping(mapping.getIRIStrEnt1()) || ignoreMapping(mapping.getIRIStrEnt2()))
					continue;
				
				if (mapping.isClassMapping())
					alignment_output.addClassMapping2Output(
							mapping.getIRIStrEnt1(), 
							mapping.getIRIStrEnt2(),
							mapping.getMappingDirection(),
							mapping.getConfidence());
				else if (mapping.isObjectPropertyMapping())
					alignment_output.addObjPropMapping2Output(
							mapping.getIRIStrEnt1(), 
							mapping.getIRIStrEnt2(),
							mapping.getMappingDirection(),
							mapping.getConfidence());
				else if (mapping.isDataPropertyMapping())
					alignment_output.addDataPropMapping2Output(
							mapping.getIRIStrEnt1(), 
							mapping.getIRIStrEnt2(),
							mapping.getMappingDirection(),
							mapping.getConfidence());
				else if (mapping.isInstanceMapping())
					alignment_output.addInstanceMapping2Output(
							mapping.getIRIStrEnt1(), 
							mapping.getIRIStrEnt2(),						
							mapping.getConfidence());
			}
			
	
			alignment_output.saveOutputFile();
		

			return alignment_output.returnAlignmentFile();
			
			
		}
		catch (Exception e){			
			LOGGER.error("Error matching with LogMap");
			LOGGER.error(e.getLocalizedMessage());
		}
		
		return null;
		
		
		
		
		
	}
	
	
	
	private boolean ignoreMapping(String uri_entity){
		//ignore mappings involving entities containing these uris
		for (String uri : Parameters.filter_entities)
			if (uri_entity.contains(uri))
				return true;		
		return false;
	}




	
	
	
	
	
	

	
}


   
   