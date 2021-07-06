package uk.ac.city.oaei.melt;
import de.uni_mannheim.informatik.dws.melt.matching_base.external.docker.MatcherDockerFile;
import de.uni_mannheim.informatik.dws.melt.matching_data.TrackRepository;
import de.uni_mannheim.informatik.dws.melt.matching_eval.ExecutionResultSet;
import de.uni_mannheim.informatik.dws.melt.matching_eval.Executor;
import de.uni_mannheim.informatik.dws.melt.matching_eval.evaluator.EvaluatorCSV;
import de.uni_mannheim.informatik.dws.melt.matching_eval.evaluator.EvaluatorBasic;
import de.uni_mannheim.informatik.dws.melt.matching_eval.evaluator.EvaluatorCopyResults;
import uk.ac.city.logmap.melt.LogMap_MELT;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EvalLogMapOAEI {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(EvalLogMapOAEI.class);
	
		
    public static void main(String[] args) throws Exception {
    
    	    	
    	File dockerFile;
    	
        dockerFile = new File("/home/ernesto/Documents/OAEI2021/evaluation/logmap-melt-oaei-2021-web-latest.tar.gz");
        //dockerFile = new File("/home/ernesto/Documents/OAEI2021/evaluation/simplewebmatcher-1.0-web-latest.tar.gz");
        
        
        MatcherDockerFile dockerMatcher;                  
        dockerMatcher = new MatcherDockerFile("logmap-melt-oaei-2021-web:latest", dockerFile);//
        //dockerMatcher = new MatcherDockerFile("simplewebmatcher-1.0-web:latest", dockerFile);//
        
        //LOGGER.info(MatcherDockerFile.getImageNameFromFile(dockerFile));
        dockerMatcher.logAllLinesFromContainer();
        
        // running the matcher on any task
        ExecutionResultSet ers = Executor.run(TrackRepository.Anatomy.Default.getFirstTestCase(), dockerMatcher);
        //ExecutionResultSet ers = Executor.run(TrackRepository.Conference.V1.getFirstTestCase(), dockerMatcher);

        
        //LogMap_MELT matcher = new LogMap_MELT();
        //ExecutionResultSet ers = Executor.run(TrackRepository.Conference.V1.getFirstTestCase(), matcher);
        //ExecutionResultSet ers = Executor.run(TrackRepository.Anatomy.Default.getFirstTestCase(), matcher);
        
        
        
        // evaluating our system
        EvaluatorCSV evaluatorCSV = new EvaluatorCSV(ers);
        EvaluatorBasic evaluatorBasic = new EvaluatorBasic(ers);        
        EvaluatorCopyResults evalCopy = new EvaluatorCopyResults(ers);

        
        //TODO
        // we should close the docker matcher so that docker cab shut down the container
        //Leave it open for debugging purposes to access the logs: docker container logs {id_container}
        //dockerMatcher.close();  
        

        // writing evaluation results to disk
        evaluatorCSV.writeToDirectory("/home/ernesto/Documents/OAEI2021/evaluation/results/");
        evaluatorBasic.writeToDirectory("/home/ernesto/Documents/OAEI2021/evaluation/results/");
        evalCopy.writeToDirectory("/home/ernesto/Documents/OAEI2021/evaluation/results/");
        //evalCopy.writeResultsToDirectory(new File("/home/ernesto/Documents/OAEI2021/evaluation/results/"));

    }
}
