package serializers;

import java.util.Arrays;

public final class TestCaseRunner<J> {
    static double measurementVals[] = new double[1000 * 1000];
    // because full gc is triggered by main loop, this should move to oldgen 
    private final Transformer<J, Object> transformer;
    private final Serializer<Object> serializer;
    private final J value;

    public TestCaseRunner(Transformer<J, Object> transformer, Serializer<Object> serializer, J value) {
        this.transformer = transformer;
        this.serializer = serializer;
        this.value = value;
    }

    public double run(TestCase tc, int iterations) throws Exception {
        return tc.run(transformer, serializer, value, iterations);
    }

    public double runWithTimeMeasurement(int timeMillis, TestCase tc, int iterations) throws Exception {
        // ruediger: 
        // 1. made this also time based (like warmup). 
        //    fast serializers get more exposure to hotspot and the slow ones will
        //    finish below 30 minute runtime :-).
        //    this is reasonable as the faster a serializer is, the more significant
        //    some few nanos become. A slow serializer being > 10seconds will not be biased
        //    by having some fewer loops.
        // 2. taking the minimum time makes results very erratic. You get very different results
        //    with each run. Especially effects of bad allocation is hidden this way, as you only
        //    capture the "good" runs.
        //    therefore i switch to average measurement (and increase warmup time to avoid measurement of
        //    unjitted runs, see Params).
        //    We can do this when running each serializer isolated in an own VM.
        long start = System.currentTimeMillis();

        int i = 0, count = 0;
        System.err.println("test-time " + timeMillis + " iteration " + iterations);
        while (System.currentTimeMillis() - start < timeMillis) {
            double time = tc.run(transformer, serializer, value, iterations);
            measurementVals[i] = time;
            if (++i >= measurementVals.length)
                i = 0;
            else if (count < i)
                count = i;
        }
        double sumTime = 0;
        for (int j = 0; j < count; j++) {
            double time = measurementVals[j];
            sumTime += time;
        }
        double avg = sumTime / count;
        Arrays.sort(measurementVals, 0, count);
        System.err.println("-----------------------------------------------------------------------------");
        System.err.println(serializer.getName());
        System.err.println("min:" + measurementVals[0]);
        System.err.println("25%ile:" + measurementVals[count / 4]);
        System.err.println("50%ile:" + measurementVals[count / 2]);
        System.err.println("75%ile:" + measurementVals[count / 4 * 3]);
        System.err.println("90%ile:" + measurementVals[count - count / 10]);
        System.err.println("99%ile:" + measurementVals[count - count / 100]);
        System.err.println("max:" + measurementVals[count - 1]);
        System.err.println("average:" + avg + "ms deviation:" + (avg - measurementVals[count / 2]) + "ms");
        System.err.println("-----------------------------------------------------------------------------");
        return avg;
    }
}
