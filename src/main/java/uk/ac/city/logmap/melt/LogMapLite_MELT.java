package uk.ac.city.logmap.melt;

import de.uni_mannheim.informatik.dws.melt.matching_base.MatcherURL;
import uk.ac.ox.krr.logmap_lite.LogMap_Lite;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LogMap Lite matcher packaging with MELT extending MatcherURL
 * @author ernesto
 *
 */
public class LogMapLite_MELT extends MatcherURL {  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogMapLite_MELT.class);

	@Override
	public URL match(URL source, URL target, URL inputAlignment)
			throws Exception {
			    
		
		try {
			
			LogMap_Lite logmap_lt = new LogMap_Lite();
			logmap_lt.align(source.toURI().toString(), target.toURI().toString());
			
			LOGGER.info("Matching with LogMapLt completed");
			
			return logmap_lt.returnAlignmentFile();
			
			
		}
		catch (Exception e){			
			LOGGER.error("Error matching with LogMap-Bio");
			LOGGER.error(e.getLocalizedMessage());
		}
		
		return null;
		
	}
	


}


