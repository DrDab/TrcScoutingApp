package tbawebapi;

/*
 * Copyright (c) 2017 Titan Robotics Club (http://www.titanrobotics.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


import java.io.PrintStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue;

/**
 * This class extends the WebRequest class and provides the TBA specific web request methods.
 */
public class TbaApiV3 extends WebRequest
{
    private static final String TBA_API_BASE = "https://www.thebluealliance.com/api/v3";
    private static final String TBA_AUTH_KEY = "UQmqq10GkWyNGmsSuN1WvKp0jpG0x4tSfaNc46E6ZGemWK6JL4sM8mPWZthOpHDN";
    private String header = null;

    /**
     * Constructor: Create an instance of the object.
     *
     * @param authorId specifies the author ID.
     * @param appId specifies the app ID.
     * @param appVersion specifies the app version.
     */
    public TbaApiV3(String authorId, String appId, String appVersion)
    {
        super(TBA_API_BASE);
        addRequestProperty("User-Agent", appId);
        addRequestProperty("X-TBA-App-Id", authorId + ":" + appId + ":" + appVersion);
        addRequestProperty("X-TBA-Auth-Key", TBA_AUTH_KEY);
    }   //TbaApiV3

    /**
     * This method prints the syntax of the API requests.
     * @param helpOut specifies standard output stream for the help message.
     */
    public void printApiHelp(PrintStream helpOut)
    {
        helpOut.print(
                "<Request>: (version 3)\n" +
                        "\tstatus\t\t\t\t\t\t\t- TBA Status request.\n" +
                        "\tteams[/<Year>]/<PageNum>[/(simple|keys)]\t\t- Team List Request with optional year and verbosity.\n" +
                        "\tteam/<TeamKey>[/simple]\t\t\t\t\t- Single Team Request with optional verbosity.\n" +
                        "\tteam/<TeamKey>/years_participated\t\t\t- Team Years Participated Request.\n" +
                        "\tteam/<TeamKey>/districts\t\t\t\t- Team Districts Request.\n" +
                        "\tteam/<TeamKey>/robots\t\t\t\t\t- Team Robots Request.\n" +
                        "\tteam/<TeamKey>/events[/<Year>][/(simple|keys)]\t\t- Team Events Request with optional year and verbosity.\n" +
                        "\tteam/<TeamKey>/event/<EventKey>/matches[/(simple|keys)]\t- Team Event Matches Request with optional verbosity.\n" +
                        "\tteam/<TeamKey>/event/<EventKey>/awards\t\t\t- Team Event Awards Request.\n" +
                        "\tteam/<TeamKey>/event/<EventKey>/status\t\t\t- Team Event Status Request.\n" +
                        "\tteam/<TeamKey>/awards[/<Year>]\t\t\t\t- Team Awards Request with optional year.\n" +
                        "\tteam/<TeamKey>/matches[/<Year>][/(simple|keys)]\t\t- Team Matches Request with optional year and verbosity.\n" +
                        "\tteam/<TeamKey>/media/<Year>\t\t\t\t- Team Media Request.\n" +
                        "\tteam/<TeamKey>/social_media\t\t\t\t- Team Social Media Request.\n" +
                        "\tevents/<Year>[/(simple|keys)]\t\t\t\t- Event List Request with optional verbosity.\n" +
                        "\tevent/<EventKey>[/simple]\t\t\t\t- Single Event Request with optional verbosity.\n" +
                        "\tevent/<EventKey>/teams[/(simple|keys)]\t\t\t- Event Teams Request with optional verbosity.\n" +
                        "\tevent/<EventKey>/alliances\t\t\t\t- Event Alliances Request.\n" +
                        "\tevent/<EventKey>/insights\t\t\t\t- Event Insights Request.\n" +
                        "\tevent/<EventKey>/oprs\t\t\t\t\t- Event OPR Request.\n" +
                        "\tevent/<EventKey>/predictions\t\t\t\t- Event Rankings Request.\n" +
                        "\tevent/<EventKey>/rankings\t\t\t\t- Event Rankings Request.\n" +
                        "\tevent/<EventKey>/district_points\t\t\t- Event District Points Request.\n" +
                        "\tevent/<EventKey>/matches[/(simple|keys)]\t\t- Event Matches Request with optional verbosity.\n" +
                        "\tevent/<EventKey>/awards\t\t\t\t\t- Event Awards Request.\n" +
                        "\tdistricts/<Year>\t\t\t\t\t- District List Request.\n" +
                        "\tdistrict/<DistrictKey>/teams[/(simple|keys)]\t\t- District Teams Request with optional verbosity.\n" +
                        "\tdistrict/<DistrictKey>/rankings\t\t\t\t- District Rankings Request.\n" +
                        "\tdistrict/<DistrictKey>/events[/(simple|keys)]\t\t- District Events Request with optional verbosity.\n" +
                        "\tmatch/<MatchKey>[/simple]\t\t\t\t- Match Request with optional verbosity.\n");
    }   //printApiHelp

    //
    // TBA API v3.
    //

    /**
     * This method sends a Status Request.
     *
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return status data.
     */
    public JsonStructure getStatus(PrintStream statusOut)
    {
        return get("status", statusOut, header);
    }   //getStatus

    /**
     * This method adds teams of the specified page to the teams array.
     *
     * @param arrBuilder specifies the array builder to add the teams into.
     * @param year specifies the optional year, null for all years.
     * @param pageNum specifies the page number.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return true if successful, false if failed.
     */
    private boolean addTeams(
            JsonArrayBuilder arrBuilder, String year, int pageNum, String verbosity, PrintStream statusOut)
    {
        String request = "teams/";
        if (year != null) request += year + "/";
        request += pageNum;
        if (verbosity != null) request += "/" + verbosity;

        JsonStructure data = get(request, statusOut, header);
        boolean success;
        if (data != null && data.getValueType() == JsonValue.ValueType.ARRAY && !((JsonArray)data).isEmpty())
        {
            for (JsonValue team: (JsonArray)data)
            {
                arrBuilder.add(team);
            }
            success = true;
        }
        else
        {
            success = false;
        }

        return success;
    }   //addTeams

    /**
     * This method sends a Team List Request.
     *
     * @param year specifies the optional year, null for all years.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team list data.
     */
    public JsonStructure getTeams(String year, String verbosity, PrintStream statusOut)
    {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (int page = 0; ; page++)
        {
            if (!addTeams(arrBuilder, year, page, verbosity, statusOut))
                break;
        }

        return arrBuilder.build();
    }   //getTeams

    /**
     * This method sends a Single Team Request.
     *
     * @param teamKey specifies the team key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return single team data.
     */
    public JsonStructure getTeam(String teamKey, String verbosity, PrintStream statusOut)
    {
        String request = "team/" + teamKey;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getTeam

    /**
     * This method sends the Team Years Participated Request.
     *
     * @param teamKey specifies the team key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team years participated data.
     */
    public JsonStructure getTeamYearsParticipated(String teamKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/years_participated", statusOut, header);
    }   //getTeamYearsParticipated

    /**
     * This method sends the Team Districts Request.
     *
     * @param teamKey specifies the team key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team districts data.
     */
    public JsonStructure getTeamDistricts(String teamKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/districts", statusOut, header);
    }   //getTeamDistricts

    /**
     * This method sends the Team Robots Request.
     *
     * @param teamKey specifies the team key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team robots data.
     */
    public JsonStructure getTeamRobots(String teamKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/robots", statusOut, header);
    }   //getTeamRobots

    /**
     * This method sends a Team Events Request.
     *
     * @param teamKey specifies the team key.
     * @param year specifies the optional year, null for all years.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team events data.
     */
    public JsonStructure getTeamEvents(String teamKey, String year, String verbosity, PrintStream statusOut)
    {
        String request = "team/" + teamKey + "/events";
        if (year != null) request += "/" + year;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getTeamEvents

    /**
     * This method sends the Team Event Matches Request.
     *
     * @param teamKey specifies the team key.
     * @param eventKey specifies the event key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team event matches data.
     */
    public JsonStructure getTeamEventMatches(String teamKey, String eventKey, String verbosity, PrintStream statusOut)
    {
        String request = "team/" + teamKey + "/event/" + eventKey + "/matches";
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getTeamEventMatches

    /**
     * This method sends the Team Event Awards Request.
     *
     * @param teamKey specifies the team key.
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team event awards data.
     */
    public JsonStructure getTeamEventAwards(String teamKey, String eventKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/event/" + eventKey + "/awards", statusOut, header);
    }   //getTeamEventAwards

    /**
     * This method sends the Team Event Status Request.
     *
     * @param teamKey specifies the team key.
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team event status data.
     */
    public JsonStructure getTeamEventStatus(String teamKey, String eventKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/event/" + eventKey + "/status", statusOut, header);
    }   //getTeamEventStatus

    /**
     * This method sends the Team Awards Request.
     *
     * @param teamKey specifies the team key.
     * @param year specifies the optional year, null for all years.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team awards data.
     */
    public JsonStructure getTeamAwards(String teamKey, String year, PrintStream statusOut)
    {
        String request = "team/" + teamKey + "/awards";
        if (year != null) request += "/" + year;
        return get(request, statusOut, header);
    }   //getTeamAwards

    /**
     * This method sends the Team Matches Request.
     *
     * @param teamKey specifies the team key.
     * @param year specifies the optional year, null for all years.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team matches data.
     */
    public JsonStructure getTeamMatches(String teamKey, String year, String verbosity, PrintStream statusOut)
    {
        String request = "team/" + teamKey + "/matches";
        if (year != null) request += "/" + year;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getTeamMatches

    /**
     * This method sends the Team Media Request.
     *
     * @param teamKey specifies the team key.
     * @param year specifies the year.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team media data.
     */
    public JsonStructure getTeamMedia(String teamKey, String year, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/media/" + year, statusOut, header);
    }   //getTeamMedia

    /**
     * This method sends the Team Social Media Request.
     *
     * @param teamKey specifies the team key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return team social media data.
     */
    public JsonStructure getTeamSocialMedia(String teamKey, PrintStream statusOut)
    {
        return get("team/" + teamKey + "/social_media", statusOut, header);
    }   //getTeamSocialMedia

    /**
     * This methods sends the Event List Request.
     *
     * @param year specifies the year.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event list data.
     */
    public JsonStructure getEvents(String year, String verbosity, PrintStream statusOut)
    {
        String request = "events/" + year;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getEvents

    /**
     * This method sends the Event Info Request.
     *
     * @param eventKey specifies the event key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event info data.
     */
    public JsonStructure getEvent(String eventKey, String verbosity, PrintStream statusOut)
    {
        String request = "event/" + eventKey;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getEvent

    /**
     * This method sends the Event Teams Request.
     *
     * @param eventKey specifies the event key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event teams data.
     */
    public JsonStructure getEventTeams(String eventKey, String verbosity, PrintStream statusOut)
    {
        String request = "event/" + eventKey + "/teams";
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getEventTeams

    /**
     * This method sends the Event Alliances Request.
     *
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event alliances data.
     */
    public JsonStructure getEventAlliances(String eventKey, PrintStream statusOut)
    {
        return get("event/" + eventKey + "/alliances", statusOut, header);
    }   //getEventAlliances

    /**
     * This method sends the Event Insights Request.
     *
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event insights data.
     */
    public JsonStructure getEventInsights(String eventKey, PrintStream statusOut)
    {
        return get("event/" + eventKey + "/insights", statusOut, header);
    }   //getEventInsights

    /**
     * This method sends the Event OPR Request.
     *
     * @param eventKey specifies the event key.
     * @param verboseLevel specifies verbose level.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event OPR data.
     */
    public JsonStructure getEventOprs(String eventKey, int verboseLevel, PrintStream statusOut)
    {
        JsonStructure data = get("event/" + eventKey + "/oprs", statusOut, header);

        if (data != null && verboseLevel < 2)
        {
            data = (JsonStructure)((JsonObject)data).get("oprs");
        }

        return data;
    }   //getEventOpr

    /**
     * This method sends the Event Predictions Request.
     *
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event rankings data.
     */
    public JsonStructure getEventPredictions(String eventKey, PrintStream statusOut)
    {
        return get("event/" + eventKey + "/predictions", statusOut, header);
    }   //getEventPreditions

    /**
     * This method sends the Event Rankings Request.
     *
     * @param eventKey specifies the event key.
     * @param verboseLevel specifies verbose level.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event rankings data.
     */
    public JsonStructure getEventRankings(String eventKey, int verboseLevel, PrintStream statusOut)
    {
        JsonStructure data = get("event/" + eventKey + "/rankings", statusOut, header);

        if (data != null && verboseLevel < 2)
        {
            data = (JsonStructure)((JsonObject)data).get("rankings");
        }

        return data;
    }   //getEventRankings

    /**
     * This method sends the Event District Points Request.
     *
     * @param eventKey specifies the event key.
     * @param verboseLevel specifies verbose level.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event district points data.
     */
    public JsonStructure getEventDistrictPoints(String eventKey, int verboseLevel, PrintStream statusOut)
    {
        JsonStructure data = get("event/" + eventKey + "/district_points", statusOut, header);

        if (data != null && verboseLevel < 2)
        {
            data = (JsonStructure)((JsonObject)data).get("points");
        }

        return data;
    }   //getEventDistrictPoints

    /**
     * This method sends the Event Matches Request.
     *
     * @param eventKey specifies the event key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event matches data.
     */
    public JsonStructure getEventMatches(String eventKey, String verbosity, PrintStream statusOut)
    {
        String request = "event/" + eventKey + "/matches";
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getEventMatches

    /**
     * This method sends the Event Awards Request.
     *
     * @param eventKey specifies the event key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return event awards data.
     */
    public JsonStructure getEventAwards(String eventKey, PrintStream statusOut)
    {
        return get("event/" + eventKey + "/awards", statusOut, header);
    }   //getEventAwards

    /**
     * This method sends the District List Request.
     *
     * @param year specifies the year.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return district list data.
     */
    public JsonStructure getDistricts(String year, PrintStream statusOut)
    {
        return get("districts/" + year, statusOut, header);
    }   //getDistricts

    /**
     * This method sends the District Teams Request.
     *
     * @param districtKey specifies the district key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return district teams data.
     */
    public JsonStructure getDistrictTeams(String districtKey, String verbosity, PrintStream statusOut)
    {
        String request = "district/" + districtKey + "/teams";
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getDistrictTeams

    /**
     * This method sends the District Rankings Request.
     *
     * @param districtKey specifies the district key.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return district rankings data.
     */
    public JsonStructure getDistrictRankings(String districtKey, PrintStream statusOut)
    {
        return get("district/" + districtKey + "/rankings", statusOut, header);
    }   //getDistrictRankings

    /**
     * This method sends the District Events Request.
     *
     * @param districtKey specifies the district key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return district events data.
     */
    public JsonStructure getDistrictEvents(String districtKey, String verbosity, PrintStream statusOut)
    {
        String request = "district/" + districtKey + "/events";
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getDistrictEvents

    /**
     * This method sends the Match Request.
     *
     * @param matchKey specifies the match key.
     * @param verbosity specifies optional verbosity, null for full verbosity.
     * @param statusOut specifies standard output stream for command status, can be null for quiet mode.
     * @return match data.
     */
    public JsonStructure getMatch(String matchKey, String verbosity, PrintStream statusOut)
    {
        String request = "match/" + matchKey;
        if (verbosity != null) request += "/" + verbosity;
        return get(request, statusOut, header);
    }   //getMatch

}   //class TbaApiV3