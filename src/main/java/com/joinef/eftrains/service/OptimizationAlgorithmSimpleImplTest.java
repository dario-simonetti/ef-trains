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

import java.util.ArrayList;
import java.util.HashMap;
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
        //journeyService = new JourneyServiceImpl(false);
        optimizationAlgorithm.setJourneyService(journeyService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPerformOptimization() throws Exception {

        when(journeyService.countStations()).thenReturn(4);

        when(journeyService.findFrom(eq(0), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(1, new Journey(1, 0, 1, null, null));
                journeys.put(2, new Journey(7, 0, 2, null, null));
                return journeys;
            }
        });

        when(journeyService.findFrom(eq(1), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(0, new Journey(1, 1, 0, null, null));
                journeys.put(2, new Journey(1, 1, 2, null, null));
                journeys.put(3, new Journey(7, 1, 3, null, null));
                return journeys;
            }
        });

        when(journeyService.findFrom(eq(2), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(0, new Journey(7, 2, 0, null, null));
                journeys.put(3, new Journey(1, 2, 3, null, null));
                journeys.put(1, new Journey(1, 2, 1, null, null));
                return journeys;
            }
        });

        when(journeyService.findFrom(eq(3), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(1, new Journey(7, 3, 1, null, null));
                journeys.put(2, new Journey(1, 3, 2, null, null));
                return journeys;
            }
        });

        List<List<Journey>> journeys = optimizationAlgorithm.performOptimization(0, 3);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(0).

                        getDepartureStation(),

                0);
        Assert.assertEquals(route.get(1).

                        getDepartureStation(),

                1);
        Assert.assertEquals(route.get(2).

                        getDepartureStation(),

                2);

    }

    @Test
    public void testPerformOptimizationTime() throws Exception {

        final int testCount = 2500;
        //when(journeyDao.countStations()).thenReturn(testCount);
        //journeyService = new JourneyServiceImpl(false);

        when(journeyService.findFrom(anyInt(), any(DateTime.class))).thenAnswer(new Answer<List<Journey>>() {
            @Override
            public List<Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                int i = (Integer) invocationOnMock.getArguments()[0];

                List<Journey> journeys = new ArrayList<Journey>();

                if (i + 1 <= testCount - 1) journeys.add(new Journey(7.0f, i, i + 1, null, null));
                if (i + 2 <= testCount - 1) journeys.add(new Journey(6.0f, i, i + 2, null, null));
                if (i + 3 <= testCount - 1) journeys.add(new Journey(5.0f, i, i + 3, null, null));
                if (i + 4 <= testCount - 1) journeys.add(new Journey(8.0f, i, i + 4, null, null));
                if (i + 5 <= testCount - 1) journeys.add(new Journey(10.0f, i, i + 5, null, null));


                return journeys;
            }
        });

        /* for(int i =0; i < testCount; i++) {
            for (int j = 0; j < testCount; j++) {
                if(j == i+1 || j == i+2) {
                    when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(1f);
                    when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(1f);
                    continue;
                }

                when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(Float.NaN);
                when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(Float.NaN);
            }
        }*/

        when(journeyService.countStations()).thenReturn(testCount);

        List<List<Journey>> journeys =  optimizationAlgorithm.performOptimization(0, testCount - 1);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(route.size()-1).getDepartureStation(), testCount - 1);
    }
}