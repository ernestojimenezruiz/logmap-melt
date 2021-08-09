package uk.ac.city.logmap.melt;

import de.uni_mannheim.informatik.dws.melt.matching_base.MatcherURL;
import uk.ac.ox.krr.logmap2.LogMap2_OAEI_BioPortal;
import uk.ac.ox.krr.logmap2.Parameters;
import uk.ac.ox.krr.logmap2.io.OAEIAlignmentOutput;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LogMap-Bio matcher packaging with MELT extending MatcherURL
 * @author ernesto
 *
 */
public class LogMapBio_MELT extends MatcherURL {  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogMapBio_MELT.class);

	@Override
	public URL match(URL source, URL target, URL inputAlignment)
			throws Exception {
			    
		
		try {
			
			LogMap2_OAEI_BioPortal logmap_bio = new LogMap2_OAEI_BioPortal("oaei-resources/");
			logmap_bio.align(source.toURI().toString(), target.toURI().toString());
			
			LOGGER.info("Matching with LogMap-Bio completed");
			
			
			OAEIAlignmentOutput alignment_output = new OAEIAlignmentOutput("alignment", logmap_bio.getURISourceOntology(), logmap_bio.getURITargetOntology());
			
			
			for (MappingObjectStr mapping : logmap_bio.getLogMapBio_Mappings()) {
				
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
			LOGGER.error("Error matching with LogMap-Bio");
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

