$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/java/features/ApplicationChecks.feature");
formatter.feature({
  "line": 1,
  "name": "Application Checks",
  "description": "",
  "id": "application-checks",
  "keyword": "Feature"
});
formatter.before({
  "duration": 4519190000,
  "status": "passed"
});
formatter.scenario({
  "line": 2,
  "name": "Web flow with USAA client",
  "description": "",
  "id": "application-checks;web-flow-with-usaa-client",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 3,
  "name": "User get data from excel sheet \"ApplicationChecks\" for health checks",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "Navigate to application and login with credentials into client and verify home page",
  "keyword": "When "
});
formatter.match({
  "arguments": [
    {
      "val": "ApplicationChecks",
      "offset": 32
    }
  ],
  "location": "CommonStepDef.userGetDataFromExcelSheetForMTRECalculations(String)"
});
formatter.result({
  "duration": 622300300,
  "status": "passed"
});
formatter.match({
  "location": "ApplicationStepDef.navigateToURLAndLoginWithCredentials()"
});
formatter.result({
  "duration": 9379819100,
  "status": "passed"
});
formatter.after({
  "duration": 608954100,
  "status": "passed"
});
});