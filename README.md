# Inkoopproces_IPASS

## **Heroku en database url:**
**Applicatie url:** https://ipass-milovoorhout.herokuapp.com/  
**Database url:** postgres://sztsazkugldniy:6f647c8fcb24278a33af5b9ebd84a777428dc5a8b6df1b9c980c7ad9481dd8cd@ec2-54-217-234-157.eu-west-1.compute.amazonaws.com:5432/ddc9lqlf6qkl9a  
  
## **Gebruikers:**  
  gebruikersnaam: 'milovoorhout' & wachtwoord: 'milotest' (Dit is een 'User')  
  gebruikersnaam: 'johanbakker' & wachtwoord: 'panda' (Dit is een 'Voorstel manager')  
  gebruikersnaam: 'annalee' & wachtwoord: 'beste' (Dit is ee 'Budget manager')  
  
## **Gebruikershandleiding:**  
### **Algemeen:**  
  Elke gebruiker kan een aankoop/product voorstel maken, dit doen zij door in te loggen en op de knop aankoop te drukken.
  Voer de gegevens in en druk op insturen dan heeft u een aankoop voorstel ingediend, wilt u een product voorstel indienden
  klik dan op het + naast de product select. Wilt u terug naar het menu klik op het pijltje links onder.  
  Elke gebruiker kan ook de status van zijn voorstellen zien. Dit is te zien bij het tabje status in menu, ga naar status
  en dan komt er een lijst met de status van al u ingediende voorstellen. Als een voorstel gekeurd is dat is er mogelijkheid
  om de status te verwijderen.  
  Als u daarna weer terug gaat naar het menu kunt u uitloggen.  
  
    
### **Voorstel manager:**  
  Een voorstel manager kan meer dingen doen dan een normale gebruiker.  
  De voorstel manager heeft bij menu keuze uit 2 extra tabjes, het tabje keuren en het tabje producten.  
  Als u naar het tabje keuren gaat kan u product voorstellen goedkeuren (die de gebruiker zelf niet heeft ingediend),
  bij de select rechts boven kunt u ook kiezen om aankoopvoorstellen te keuren (dit zijn wederom voorstellen die u zelf 
  niet heeft ingediend). Bij product/aankoop voorstellen keuren ziet u 2 knoppen een groene knop en een rode knop. 
  De rode knop keurt het voorstel af en de groene knop keurt het voorstel goed. Als u bij de select knop heeft gekozen voor
  aankoop voorstellen verschijnt er ook een budget tabel. In deze tabel kunt u per afdeling het budget zien, zo kunt u
  per aankoop voorstel kijken of er genoeg budget is om dit te betalen (Bij het goedkeuren van een aankoop voorstel wordt
  ook gelijk het bedrag van de aankoop van het budget afgehaald). Als u ziet dat er teweinig budget is kunt u op de knop
  'budget +' klikken dit geeft een scherm waar u een budget voorstel kan doen. Klik op insturen en het budget voorstel
  wordt aangemaakt en op gestuurd. Bij status kan u nu, u voorstel zien.  
  
  Voorstel managers kunnen via menu ook kiezen op naar het tabje producten te gaan. Eenmaal op deze pagina zien zij
  een lijst met alle producten. In deze lijst kunnen zij zoeken door middel van de zoek balk boven aan. Als zij het juiste
  product hebben gevonden kunnen zij deze aanpassen/verwijderen. Wordt het product verwijderd dan woorden alle voorstellen
  met dat product verwijderd en krijgen die mensen die dat product al voorstel hadden als status 'Product verwijderd'.
  Wordt het product aangepast dan gebeurd er niks bij de aankoop voorstellen en word de product naam bij Statussen aangepast
  naar de nieuwe product naam.  
    
      
### **Budget manager:**  
  Budget managers hebben een extra functie in hun menu zei kunnen namelijk budget voorstellen keuren.  
  Dit doen zij door op het tabje keuren te klikken. Een budget manager zier hier alle budget voorstellen die er zijn,
  naast elk voorstel staan weer 2 knoppen 1 groenen en 1 rode knop. De groene knop keurt het voorstel goed en de
  rode knop keurt het voorstel af. Als u op de groenen knop drukt wordt het budget automatisch geupdate met de 
  aangevraagde vegroting. Als u hem afkeurt gebeurd er niks behalven dat het voorstel verwijderd wordt.  
  De groene knop zorgt er voor dat de status van het voorstel 'Goed gekeurd' wordt en de rode knop zorgt dat de status 
  van het voorsetl 'Afgekeurd wordt'.  
    
  Dit zijn alle functionaliteiten die de verschillenden gebruikers kunnen uitvoeren.
