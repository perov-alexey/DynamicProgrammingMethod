import org.apache.commons.lang3.SerializationUtils;
import pojo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Algorithm {

    public static Field execute(Field field) {
        List<Field> solutions = new LinkedList<>();
        solutions.add(SerializationUtils.clone(field));

        for (Link link : field.getLinks()) {
            List<Field> temp = new ArrayList<>(solutions);

            for (Field solution : temp) {
                solutions.remove(solution);

                Field topField = SerializationUtils.clone(solution);
                Trace topTrace = new Trace();
                topTrace.setLink(link);
                topField.addTrace(Tracer.trace(topField, topTrace, true));

                Field bottomField = SerializationUtils.clone(solution);
                Trace bottomTrace = new Trace();
                bottomTrace.setLink(link);
                bottomField.addTrace(Tracer.trace(bottomField, bottomTrace, false));

                if (!topField.hasOverloadedChannel()) solutions.add(topField);
                if (!bottomField.hasOverloadedChannel()) solutions.add(bottomField);
            }
        }

        Field bestSolution = solutions.get(0);
        for (Field solution : solutions) {
            if (solution.getGrade() < bestSolution.getGrade()) {
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

}
