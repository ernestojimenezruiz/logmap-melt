package uk.ac.city.logmap.melt;


import de.uni_mannheim.informatik.dws.melt.matching_owlapi.MatcherYAAAOwlApi;
import de.uni_mannheim.informatik.dws.melt.yet_another_alignment_api.Alignment;
import de.uni_mannheim.informatik.dws.melt.yet_another_alignment_api.CorrespondenceRelation;

import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.Parameters;

import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;


import java.util.Properties;

import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LogMap matcher packaging with MELT
 * @author ernesto
 *
 */
public class LogMap_MELT_OWLAPI extends MatcherYAAAOwlApi {  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogMap_MELT_OWLAPI.class);

	
	
	public Alignment match(OWLOntology source, OWLOntology target, Alignment inputAlignment, Properties p)
			throws Exception {

		
		Alignment alignment = new Alignment();
		
		try {
					
			LogMap2_Matcher logmap = new LogMap2_Matcher(source, target, "oaei-resources/");
						
			LOGGER.info("Matching with LogMap completed");
			
			 
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
			LOGGER.error("Error matching with LogMap");
			LOGGER.error(e.getLocalizedMessage());
		}
		
		return alignment;
		
		
	}
	

	
	
	
	private boolean ignoreMapping(String uri_entity){
		//ignore mappings involving entities containing these uris
		for (String uri : Parameters.filter_entities)
			if (uri_entity.contains(uri))
				return true;		
		return false;
	}




	
	
	
	
	
	

	
}


   
   