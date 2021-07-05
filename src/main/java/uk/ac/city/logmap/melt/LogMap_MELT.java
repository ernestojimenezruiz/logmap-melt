package uk.ac.city.logmap.melt;

import de.uni_mannheim.informatik.dws.melt.matching_base.MatcherURL;
//import de.uni_mannheim.informatik.dws.melt.matching_owlapi.MatcherYAAAOwlApi;
//import de.uni_mannheim.informatik.dws.melt.yet_another_alignment_api.Alignment;
//import de.uni_mannheim.informatik.dws.melt.yet_another_alignment_api.CorrespondenceRelation;
//import de.uni_mannheim.informatik.dws.melt.matching_base.MatcherURL;
import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.Parameters;
import uk.ac.ox.krr.logmap2.io.OAEIAlignmentOutput;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;
//import uk.ac.ox.krr.logmap2.utilities.Utilities;

import java.net.URL;
//import java.util.Properties;
import java.util.Properties;

import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

//import org.semanticweb.owlapi.model.OWLOntology;



/**
 * LogMap matcher packaging with MELT
 * @author ernesto
 *
 */
public class LogMap_MELT extends MatcherURL { //MatcherYAAAOwlApi { //// 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogMap_MELT.class);

	
	/*
	public Alignment match(OWLOntology source, OWLOntology target, Alignment inputAlignment, Properties p)
			throws Exception {

		
		Alignment alignment = new Alignment();
		
		try {
			
			LOGGER.info("In LogMap MELT");
		
			LogMap2_Matcher logmap = new LogMap2_Matcher(source, target, "oaei-resources/");
			
			//System.out.println("Number of mappings: " + logmap.getLogmap2_Mappings().size());
			
			LOGGER.info("After LogMap");
			
			 
			for (MappingObjectStr mapping : logmap.getLogmap2_Mappings()) {
				
				CorrespondenceRelation relation;
				
				if (mapping.getMappingDirection()==MappingObjectStr.EQ)
					relation = CorrespondenceRelation.EQUIVALENCE;
				else if (mapping.getMappingDirection()==MappingObjectStr.SUB)
					relation = CorrespondenceRelation.SUBSUMED;
				else if (mapping.getMappingDirection()==MappingObjectStr.SUP)
					relation = CorrespondenceRelation.SUBSUME;
				else
					relation = CorrespondenceRelation.UNKNOWN;
			
				alignment.add(
						mapping.getIRIStrEnt1(),
						mapping.getIRIStrEnt2(), 
						mapping.getConfidence(), 
						relation);
			}
			
			
		}
		catch (Exception e){
			//System.err.println("Error");
			//e.printStackTrace();
			//LOGGER.trace("Error");
			LOGGER.error("Error");
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error(e.getMessage());
		}
		
		return alignment;
		
		
	}
	*/
	

	@Override
	public URL match(URL source, URL target, URL inputAlignment)
			throws Exception {
		
		
		//System.out.println("LALALA");
		//Logger.getRootLogger().setLevel(Level.ERROR);
		
		//Parameters read in LogMap, make sure in right path
	    //<!-- Accessing the resources:
	     //   - all files in "oaei-resources" folder are stored in the current working directory and can be accessed with 
	      //        Files.readAllLines(Paths.get("oaei-resources", "configuration_oaei.txt"));
	     //   - all files in "src/main/resources" folder are compiled to the resulting jar and can be accessed with
	     //         getClass().getClassLoader().getResourceAsStream("configuration_jar.txt");	      
		//
		
		
		
		try {
			
			LOGGER.info("In LogMap MELT");
		
			LogMap2_Matcher logmap = new LogMap2_Matcher(source.toURI().toString(), target.toURI().toString(), "oaei-resources/");
			
			//System.out.println("Number of mappings: " + logmap.getLogmap2_Mappings().size());
			
			LOGGER.info("After LogMap");
			
			
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
			//System.err.println("Error");
			//e.printStackTrace();
			//LOGGER.trace("Error");
			LOGGER.error("Error");
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error(e.getMessage());
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


   
   