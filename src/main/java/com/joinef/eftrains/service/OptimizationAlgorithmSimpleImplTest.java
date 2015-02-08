package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OptimizationAlgorithmSimpleImplTest {


    @Mock
    private JourneyService journeyService;

    private OptimizationAlgorithmSimpleImpl optimizationAlgorithm;

    @Before
    public void setUp() throws Exception {
        optimizationAlgorithm = new OptimizationAlgorithmSimpleImpl();
        optimizationAlgorithm.setJourneyService(journeyService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPerformOptimization() throws Exception {
        when(journeyService.find(eq(0), eq(1), any(DateTime.class))).thenReturn(1f);
        when(journeyService.find(eq(0), eq(2), any(DateTime.class))).thenReturn(7f);
        when(journeyService.find(eq(1), eq(2), any(DateTime.class))).thenReturn(1f);
        when(journeyService.find(eq(1), eq(3), any(DateTime.class))).thenReturn(7f);
        when(journeyService.find(eq(2), eq(3), any(DateTime.class))).thenReturn(1f);

        when(journeyService.find(eq(1), eq(0), any(DateTime.class))).thenReturn(1f);
        when(journeyService.find(eq(2), eq(0), any(DateTime.class))).thenReturn(7f);
        when(journeyService.find(eq(2), eq(1), any(DateTime.class))).thenReturn(1f);
        when(journeyService.find(eq(3), eq(1), any(DateTime.class))).thenReturn(7f);
        when(journeyService.find(eq(3), eq(2), any(DateTime.class))).thenReturn(1f);

        when(journeyService.find(eq(0), eq(3), any(DateTime.class))).thenReturn(Float.NaN);
        when(journeyService.find(eq(0), eq(0), any(DateTime.class))).thenReturn(Float.NaN);
        when(journeyService.find(eq(1), eq(1), any(DateTime.class))).thenReturn(Float.NaN);
        when(journeyService.find(eq(2), eq(2), any(DateTime.class))).thenReturn(Float.NaN);
        when(journeyService.find(eq(3), eq(3), any(DateTime.class))).thenReturn(Float.NaN);

        when(journeyService.countStations()).thenReturn(4);

        List<List<Journey>> journeys =  optimizationAlgorithm.performOptimization(0, 3);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(0).getDepartureStation(), 0);
        Assert.assertEquals(route.get(1).getDepartureStation(), 1);
        Assert.assertEquals(route.get(2).getDepartureStation(), 2);
        Assert.assertEquals(route.get(3).getDepartureStation(), 3);

    }

    @Test
    public void testPerformOptimizationTime() throws Exception {

        int testCount = 1000;

        when(journeyService.find(anyInt(), anyInt(), any(DateTime.class))).thenAnswer(new Answer<Float>() {

            @Override
            public Float answer(InvocationOnMock invocationOnMock) throws Throwable {
                int i = (Integer) invocationOnMock.getArguments()[0];
                int j = (Integer) invocationOnMock.getArguments()[1];

                if (j == i + 1 || j == i + 2) {
                    return 1.0f;
                }

                return Float.NaN;
            }
        });
        /*
        for(int i =0; i < testCount; i++) {
            for (int j = 0; j < testCount; j++) {
                if(j == i+1 || j == i+2) {
                    when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(1f);
                    when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(1f);
                    continue;
                }

                when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(Float.NaN);
                when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(Float.NaN);
            }
        }
*/
        when(journeyService.countStations()).thenReturn(testCount);

        List<List<Journey>> journeys =  optimizationAlgorithm.performOptimization(0, testCount - 1);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(route.size()-1).getDepartureStation(), testCount - 1);
    }
}