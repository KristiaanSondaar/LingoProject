#Vulnarability Analysis

## A1:2017-Injection

### Description
Injection vulnarability gaat kort gezegd over misbruik maken van queries, dit kan met SQL, NoSQL ORM, LDAP en nog veel meer. 
De aanvaller maakt slim gebruik van bijvoorbeeld een SQL aanvraag om alle accounts van een ORM systeem terug te krijgen zoals in onderstaand voorbeeld:

Query HQLQuery = session.createQuery("FROM accounts WHERE custID='" + request.getParameter("id") + "'");

Als in deze query vervolgens een request met:
http://example.com/app/accountView?id=' or '1'='1
word aangevraag worden alle records van de "accounts" table terug gestuurd omdat de ID parameter leeg is maar er "or 1 = 1" staat wat altijd True geeft.



### Risk
Het risico binnen dit project is vrij klein omdat er geen gevoelige data wordt opgeslagen in de database.
Zelfs als we authenticatie en autorisatie toevoegen zou het in dit project geen echte meerwaarde geven.
Als het project een systeem had waar je moest inloggen om je highscore en andere scores te kunnen bijhouden wel.

### Counter-measures
We gebruiken in dit project JPARepositories, een onderdeel van het Spring framework, om alle queries te regelen.
Hierin worden alleen parametered/named queries gebruikt en maken wij zelf geen nieuwe queries aan.

Natuurlijk is het nooit 100% veilig en zal er vast een manier hier omheen te werken, maar het wordt lastig om in JPA veranderingen te maken.


## A10:2017-Insufficient Logging

### Description
Een applicatie is gevoelig voor Insufficient Logging als er zoals de naam al zegt niet voldoende of helemaal niet gebruik wordt gemaakt van logging.
Logging is bijvoorbeeld het opslaan van accounts die inloggen in je systeem met tijd en datum. Of transacties die worden gedaan worden opgeslagen zodat er later als er problemen zijn
weer naar kan worden gekeken.
Ook is het belangrijk om software en mensen in te zetten om eventuele situaties te kunnen afhandelen, monitoring.
Een extreem voorbeeld van dit als je niks opslaat is dat een bank medewerker 100 miljoen op zijn eigen rekening kan storten en niemand weet wie dat heeft gedaan, omdat er niks van zijn actie is opgeslagen.

### Risk
Het risico is er wel in dit project omdat er buiten de database van het spel zelf geen andere acties worden opgeslagen. 
Als er met authenticatie en autorisatie zou worden gewerkt en aanpassingen aan de database worden gemaakt kan dit probleem voor een groot deel worden opgelost.

### Counter-measures
Er is in dit project niet veel aandacht geplaatst op dit onderdeel. Wel zijn er veel exceptions en zijn er geen extreme dingen die kunnen gebeuren die logging nodig hebben.
Als er een highscore of iets zou worden toegevoegd zou dit een ander verhaal zijn omdat mensen dan een reden hebben om misbruik te maken van geen logging.


## A9:2017-Using components with Known Vulnerabilities

### Description
Het gebruiken van componenten waarvan je weet dat er security issues mee zijn. 
Als je in je project gebruik gaat maken van een nieuwe library of component is het altijd belangrijk om te checken, wordt er actief aan gewerkt of zijn er problemen geweest in het verleden.
Want als iemand achter een kwetsbaar heid komt in zo een component is het moeilijk om je hele applicatie te herschrijven zonder dat component, en ben je afhankelijk van deze derde partij
om het probleem zo snel mogelijk op te lossen.
Een ander voorbeeld kan zijn dat je een systeem bouwt met Tensorflow OCR technologie, en ze gaan ineens overstappen naar een nieuwe versie hiervan, dan MOET jij ook mee omdat de oude niet 
meer geupdate wordt er uiteidelijk issues gaan komen waardoor jouw applicatie niet meer veilig is.

### Risk
Het risico is niet heel groot in dit systeem omdat we behalve testing components en libraries niet afhankelijk zijn van andere partijen.

### Counter-measures
Wel hebben we Dependabot toegevoegd om te zorgen dat alle libraries die we gebruiken automatisch worden geupdate wanneer dit nodig is. 
Zo krijgen we meteen een melding in de vorm van een pull request dat een librarie out of date is. Gebruik maken van Maven helpt ook met het handelen met deze libraries.
