package com.sevenre.triastest;

import com.squareup.okhttp.*;

import java.io.IOException;

/**
 * Created by shah on 6/23/2017.
 */
public class TriasService {

    private static OkHttpClient client = new OkHttpClient();
    private static MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/xml; charset=utf-8");

    private static final String URL = "http://trias.vrn.de:8080/Middleware/Data/trias";

    public String stopLocRequestByGeo(double lat, double lon) throws IOException {
        String body = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
                "<Trias xmlns=\"trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" version=\"1.0\">" +
                " <ServiceRequest>" +
                "  <RequestPayload>" +
                "   <LocationInformationRequest>" +
                "    <InitialInput>" +
                "     <GeoRestriction>" +
                "      <Circle>" +
                "       <Center>" +
                "        <Longitude>" + lon + "</Longitude>" +
                "        <Latitude>" + lat + "</Latitude>" +
                "       </Center>" +
                "        <Radius>1000000</Radius>" +
                "       </Circle>" +
                "     </GeoRestriction>" +
                "    </InitialInput>" +
                "    <Restrictions>" +
                "     <Type>stop</Type>" +
                "    </Restrictions>" +
                "   </LocationInformationRequest>" +
                "  </RequestPayload>" +
                " </ServiceRequest>" +
                "</Trias>";
        return post(body);
    }


    public String stopEventRequestByStopID(String stopId, int numberOfResult) throws IOException {
        String body = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
                "<Trias xmlns=\"trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" version=\"1.0\">" +
                "<ServiceRequest>" +
                " <RequestPayload>" +
                "  <StopEventRequest>" +
                "   <Location>" +
                "    <LocationRef>" +
                "     <StopPointRef>" + stopId + "</StopPointRef>" +
                "     <LocationName>" +
                "      <Text></Text>" +
                "     </LocationName>" +
                "    </LocationRef>" +
                "   </Location>" +
                "   <Params>" +
                "    <Type>stop</Type>" +
                "    <Language>de</Language>" +
                "    <NumberOfResults>" + numberOfResult + "</NumberOfResults>" +
                "    <PtModeFilter>" +
                "     <Exclude>false</Exclude>" +
                "     <PtMode>bus</PtMode>" +
                "     <PtMode>tram</PtMode>" +
                "     <PtMode>coach</PtMode>" +
                "     <PtMode>rail</PtMode>" +
                "    </PtModeFilter>" +
                "    <IncludeRealtimeData>true</IncludeRealtimeData>" +
                "    <StopEventType>departure</StopEventType>" +
                "   </Params>" +
                "  </StopEventRequest>" +
                " </RequestPayload>" +
                "</ServiceRequest>" +
                "</Trias>";
        return post(body);
    }

    private static String post(String body) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, body))
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }

}
