package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import clientrequest.*;
import clientresponse.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

/*
class ClientTest {

    private MockServer mockServer;
    private Client client;
    private Gson gson;

    @BeforeEach
    public void setup() {
        mockServer = new MockServer();
        mockServer.start();

        try {
            client = new Client("localhost", MockServer.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gson = new Gson();
    }

    @AfterEach
    public void tearDown() {
        try {
            client.close();
            mockServer.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRegistrationRequest() throws IOException {
        RegistrationRequest request = new RegistrationRequest("user123");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }
*/

/*    @Test
    void testRegistrationResponseParsing() throws IOException {
        RegistrationResponse registrationResponse = new RegistrationResponse("success", "Registered successfully");
        String jsonResponse = gson.toJson(registrationResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof RegistrationResponse);

        RegistrationResponse parsedResponse = (RegistrationResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Registered successfully", parsedResponse.message);
    }*/

/*
    @Test
    void testAuthorizationRequest() throws IOException {
        AuthorizationRequest request = new AuthorizationRequest("user123");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }
*/

/*    @Test
    void testAuthorizationResponseParsing() throws IOException {
        AuthorizationResponse authorizationResponse = new AuthorizationResponse("success");
        String jsonResponse = gson.toJson(authorizationResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof AuthorizationResponse);

        AuthorizationResponse parsedResponse = (AuthorizationResponse) response;
        assertEquals("success", parsedResponse.status);
    }*/

/*    @Test
    void testViewAvailableRoomsRequest() throws IOException {
        ViewAvailableRoomsRequest request = new ViewAvailableRoomsRequest();

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testViewAvailableRoomsResponseParsing() throws IOException {
        ViewAvailableRoomsResponse viewAvailableRoomsResponse = new ViewAvailableRoomsResponse("success", "Rooms available", List.of("room1", "room2"));
        String jsonResponse = gson.toJson(viewAvailableRoomsResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof ViewAvailableRoomsResponse);

        ViewAvailableRoomsResponse parsedResponse = (ViewAvailableRoomsResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Rooms available", parsedResponse.message);
        assertEquals(List.of("room1", "room2"), parsedResponse.roomIds);
    }

    @Test
    void testViewStartedGamesRequest() throws IOException {
        ViewStartedGamesRequest request = new ViewStartedGamesRequest();

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testViewStartedGamesResponseParsing() throws IOException {
        ViewStartedGamesResponse viewStartedGamesResponse = new ViewStartedGamesResponse("success", "Games in progress", List.of("game1", "game2"));
        String jsonResponse = gson.toJson(viewStartedGamesResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof ViewStartedGamesResponse);

        ViewStartedGamesResponse parsedResponse = (ViewStartedGamesResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Games in progress", parsedResponse.message);
        assertEquals(List.of("game1", "game2"), parsedResponse.roomIds);
    }

    @Test
    void testConnectForViewRequest() throws IOException {
        ConnectForViewRequest request = new ConnectForViewRequest();

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testConnectForViewResponseParsing() throws IOException {
        ConnectForViewResponse connectForViewResponse = new ConnectForViewResponse("success", "Connected for viewing");
        String jsonResponse = gson.toJson(connectForViewResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof ConnectForViewResponse);

        ConnectForViewResponse parsedResponse = (ConnectForViewResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Connected for viewing", parsedResponse.message);
    }

    @Test
    void testGameVsHumanCreateRoomRequest() throws IOException {
        GameVsHumanCreateRoomRequest request = new GameVsHumanCreateRoomRequest("white", "blitz");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testGameVsHumanCreateRoomResponseParsing() throws IOException {
        GameVsHumanCreateRoomResponse createRoomResponse = new GameVsHumanCreateRoomResponse("success", "123456");
        String jsonResponse = gson.toJson(createRoomResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameVsHumanCreateRoomResponse);

        GameVsHumanCreateRoomResponse parsedResponse = (GameVsHumanCreateRoomResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("123456", parsedResponse.roomId);
    }

    @Test
    void testGameVsHumanFindRoomRequest() throws IOException {
        GameVsHumanFindRoomRequest request = new GameVsHumanFindRoomRequest("white");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testGameVsHumanFindRoomResponseParsing() throws IOException {
        List<GameVsHumanFindRoomResponse.AvailableRoomInfo> availableRooms = List.of(
                new GameVsHumanFindRoomResponse.AvailableRoomInfo("room1", "blitz"),
                new GameVsHumanFindRoomResponse.AvailableRoomInfo("room2", "rapid")
        );
        GameVsHumanFindRoomResponse findRoomResponse = new GameVsHumanFindRoomResponse("success", availableRooms);
        String jsonResponse = gson.toJson(findRoomResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameVsHumanFindRoomResponse);

        GameVsHumanFindRoomResponse parsedResponse = (GameVsHumanFindRoomResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals(availableRooms.size(), parsedResponse.availableRooms.size());
        for (int i = 0; i < availableRooms.size(); i++) {
            assertEquals(availableRooms.get(i).roomId, parsedResponse.availableRooms.get(i).roomId);
            assertEquals(availableRooms.get(i).timeControl, parsedResponse.availableRooms.get(i).timeControl);
        }
    }

    @Test
    void testGameVsHumanConnectToRoomRequest() throws IOException {
        GameVsHumanConnectToRoomRequest request = new GameVsHumanConnectToRoomRequest("123456");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testGameVsHumanConnectToRoomResponseParsing() throws IOException {
        GameVsHumanConnectToRoomResponse connectToRoomResponse = new GameVsHumanConnectToRoomResponse("success", "Connected to room");
        String jsonResponse = gson.toJson(connectToRoomResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameVsHumanConnectToRoomResponse);

        GameVsHumanConnectToRoomResponse parsedResponse = (GameVsHumanConnectToRoomResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Connected to room", parsedResponse.message);
    }

    @Test
    void testPlayVsBotRequest() throws IOException {
        PlayVsBotRequest request = new PlayVsBotRequest();

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testPlayVsBotResponseParsing() throws IOException {
        PlayVsBotResponse playVsBotResponse = new PlayVsBotResponse("success");
        String jsonResponse = gson.toJson(playVsBotResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof PlayVsBotResponse);

        PlayVsBotResponse parsedResponse = (PlayVsBotResponse) response;
        assertEquals("success", parsedResponse.status);
    }

    @Test
    void testChooseColorRequest() throws IOException {
        ChooseColorRequest request = new ChooseColorRequest("white");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testChooseColorResponseParsing() throws IOException {
        ChooseColorResponse chooseColorResponse = new ChooseColorResponse("success", "Color chosen");
        String jsonResponse = gson.toJson(chooseColorResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof ChooseColorResponse);

        ChooseColorResponse parsedResponse = (ChooseColorResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Color chosen", parsedResponse.message);
    }

    @Test
    void testChooseDifficultyRequest() throws IOException {
        ChooseDifficultyRequest request = new ChooseDifficultyRequest("hard");

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testChooseDifficultyResponseParsing() throws IOException {
        ChooseDifficultyResponse chooseDifficultyResponse = new ChooseDifficultyResponse("success", "Difficulty chosen");
        String jsonResponse = gson.toJson(chooseDifficultyResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof ChooseDifficultyResponse);

        ChooseDifficultyResponse parsedResponse = (ChooseDifficultyResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Difficulty chosen", parsedResponse.message);
    }

    @Test
    void testGameMoveRequest() throws IOException {
        GameMoveRequest request = new GameMoveRequest("player1", "room123", 2, 3);

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testGameMoveResponseParsing() throws IOException {
        GameMoveResponse gameMoveResponse = new GameMoveResponse("success", "updated_board_here");
        String jsonResponse = gson.toJson(gameMoveResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameMoveResponse);

        GameMoveResponse parsedResponse = (GameMoveResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("updated_board_here", parsedResponse.board);
    }

    @Test
    void testGameOverWantReMatchRequest() throws IOException {
        GameOverWantReMatchRequest request = new GameOverWantReMatchRequest();

        client.sendRequest(request);
        String expectedJson = gson.toJson(request) + "\n";
        assertEquals(expectedJson, mockServer.getLastReceived());
    }

    @Test
    void testGameOverWantReMatchResponseParsing() throws IOException {
        GameOverWantReMatchResponse gameOverResponse = new GameOverWantReMatchResponse("success", "Want rematch");
        String jsonResponse = gson.toJson(gameOverResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameOverWantReMatchResponse);

        GameOverWantReMatchResponse parsedResponse = (GameOverWantReMatchResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Want rematch", parsedResponse.message);
    }

    @Test
    void testGameOverDoYouWantReMatchResponseParsing() throws IOException {
        GameOverDoYouWantReMatchResponse gameOverResponse = new GameOverDoYouWantReMatchResponse("success", "Do you want rematch?");
        String jsonResponse = gson.toJson(gameOverResponse);

        mockServer.setNextResponse(jsonResponse);

        Response response = client.getResponse();
        assertTrue(response instanceof GameOverDoYouWantReMatchResponse);

        GameOverDoYouWantReMatchResponse parsedResponse = (GameOverDoYouWantReMatchResponse) response;
        assertEquals("success", parsedResponse.status);
        assertEquals("Do you want rematch?", parsedResponse.message);
    }*/
//}


