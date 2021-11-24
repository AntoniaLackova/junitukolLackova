package cz.czechitas.selenium;

import com.google.errorprone.annotations.Var;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    WebDriver prohlizec;
    Date narozeni = new Date();

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/"); }

        //Zadání
//Vytvořte automatizované testy:
// 1. Rodič s existujícím účtem se musí být schopen přihlásit do webové aplikace.
//Poznámka: Nepište automatizovaný test na zakládání nového účtu rodiče. Účet si připravte dopředu ručně.


@Test
public void prihlasitExistujicihoUzivateleJeFunkcni() {

    prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
    prihlaseni();
    vyplnitPoleEmail("Evzen.houzvicka@gmail.com");
    vyplnitPoleHeslo("Evzenloveczen007");
    potvrditPrihlaseniUzivatele();

    Assertions.assertEquals("Přihlášky", prohlizec.findElement(By.xpath("//h1")).getText());}


//2. Rodič musí být schopen vybrat kurz, přihlásit se do aplikace a přihlásit na kurz svoje dítě.
//Poznámka: I zde použijte už existující účet rodiče, jen se k němu v průběhu testu přihlašte.
//Poznámka: Úspěšné přihlášení dítěte na kurz je třeba po vyplnění přihlášky ověřit ve svém seznamu přihlášek.

    @Test

    public void prihlaseniZakaNaKurzJeFunkcni() {

        prihlasitExistujicihoUzivateleJeFunkcni();
        vytvoreniNovePrihlasky();
        viceInformaci(1);
        zaseznovuprihlaseni();
        rozklikniTermin();
        vyberTerminKurzu(1);
        vyplnitJmenoZaka("Adam");
        vyplnitPrijmeniZaka("Houžvička");
        vyplnDatumNarozeni("12.12.2010");
        vyberZpusobUhrady(2);
        checkboxjedna();
        vypsatZdravotniOmezeni("je nemocnej na hlavičku");
        polePoznamka("ať nebere drogy");
        checkboxdve();
        prihlaseniPosledniVprihlasce();
        zkontrolovatPrihlasku();

        Assertions.assertTrue(prohlizec.getCurrentUrl().endsWith("/zaci")); }

//3. Rodič se musí být schopen přihlásit do aplikace, vyhledat kurz a přihlásit na něj svoje dítě.
//Poznámka: I zde použijte pro přihlášení do aplikace existující účet rodiče a nezapomeňte ověřit,
//že přihláška na kurz proběhla úspěšně (v rodičově seznamu přihlášek).

    @Test
    public void  vsechnoFungujePlusOvereniVrodicovskemSeznamuJeOk() {

        prihlaseniZakaNaKurzJeFunkcni();
        WebElement AdamHouzvicka = prohlizec.findElement(By.xpath("//td[@class = \"dtr-control sorting_1\" ]"));
        String Adam = AdamHouzvicka.getText();

        Assertions.assertEquals("Adam Houžvička",Adam); }

        public void prihlaseni() {
        WebElement prihlaseni = prohlizec.findElement(By.xpath("//nav/div/div[2]/a"));
        prihlaseni.click(); }


        public void vyplnitPoleEmail(String zadejEmail) {
        WebElement mail = prohlizec.findElement(By.id("email"));
        mail.sendKeys(zadejEmail);}

        public void vyplnitPoleHeslo(String zadejPassword) {
        WebElement poleUzivatelskeHeslo = prohlizec.findElement(By.id("password"));
        poleUzivatelskeHeslo.sendKeys(zadejPassword); }

        public void potvrditPrihlaseniUzivatele() {
        WebElement prihlasit = prohlizec.findElement(By.className("btn"));
        prihlasit.click(); }

    public void vytvoreniNovePrihlasky() {
        WebElement vytvoritNovouPrihlasku = prohlizec.findElement(By.className("btn"));
        vytvoritNovouPrihlasku.click();  }

        public void viceInformaci(int ktereInfo) {
        List<WebElement> vyberViceInformaci = prohlizec.findElements(By.xpath("//div[@class='card']//a[text()='Více informací']"));
        WebElement viceInfo = vyberViceInformaci.get(ktereInfo);
        viceInfo.click();}

        public void zaseznovuprihlaseni() {
        WebElement dalsiPrihlaseni = prohlizec.findElement(By.xpath("//div[2]/div/div/div[2]/a"));
        dalsiPrihlaseni.click(); }

        private void  rozklikniTermin() {
        WebElement rozklikavaciSipka = prohlizec.findElement(By.xpath("//button[@role = 'combobox' ]"));
        rozklikavaciSipka.click(); }

        private void vyberTerminKurzu(int poradiKurzu) {
        List<WebElement> kolonkyKurzu = prohlizec.findElements(By.xpath("//div/form/table/tbody/tr[2]/td[2]/div/div/div[2]/ul/li"));
        WebElement kolonka = kolonkyKurzu.get(poradiKurzu);
        kolonka.click();}

        private void vyplnitJmenoZaka(String jmenoZaka) {
        WebElement jmenoZakaPole = prohlizec.findElement(By.xpath("//*[@id=\"forename\"]"));
        jmenoZakaPole.sendKeys(jmenoZaka); }

        private void vyplnitPrijmeniZaka(String PrijmeniZaka) {
        WebElement PrijmeniZakaPole = prohlizec.findElement(By.xpath("//*[@id=\"surname\"]"));
        PrijmeniZakaPole.sendKeys(PrijmeniZaka); }

        public void vyplnDatumNarozeni(String datumNarozeniVyplnit) {
        WebElement datumNarozeniZaka = prohlizec.findElement(By.xpath("//*[@id=\"birthday\"]"));
        datumNarozeniZaka.sendKeys(datumNarozeniVyplnit); }

        private void vyberZpusobUhrady(int poradiUhrady) {
        List<WebElement> tlacitkaUhrady = prohlizec.findElements(By.xpath("//*[@class= \"custom-control-label\"]"));
        WebElement tlacitkoUhrady = tlacitkaUhrady.get(poradiUhrady);
        tlacitkoUhrady.click();}

        public void checkboxjedna() {
        WebElement box = prohlizec.findElement(By.xpath("//div/form/table/tbody/tr[9]/td[2]/span/label"));
        box.click(); }

        public void vypsatZdravotniOmezeni(String omezeni) {
        WebElement vypsatOmezeni = prohlizec.findElement(By.xpath("//*[@id=\"restrictions\"]"));
        vypsatOmezeni.click();
        vypsatOmezeni.sendKeys(omezeni); }

        public void polePoznamka(String poznamka) {
        WebElement vypsatPoznamku = prohlizec.findElement(By.xpath("//*[@id=\"note\"]"));
        vypsatPoznamku.click();
        vypsatPoznamku.sendKeys(poznamka); }

        public void checkboxdve() {
        WebElement boxdve = prohlizec.findElement(By.xpath("//tr[11]/td[2]/span"));
        boxdve.click(); }

        public void prihlaseniPosledniVprihlasce() {
        WebElement posledniprihlaseni = prohlizec.findElement(By.xpath("//input[@class=\"btn btn-primary mt-3\"]"));
        posledniprihlaseni.click(); }

        public void zkontrolovatPrihlasku() {
        WebElement prihlasky = prohlizec.findElement(By.xpath("//nav/div/div[1]/a[2]"));
        prihlasky.click(); }

        public void proRodice() {
            WebElement klikNaProRodice = prohlizec.findElement(By.xpath("//div[1]/div/a[@role='button']"));
            klikNaProRodice.click();
            WebElement klikNaFormulare = prohlizec.findElement(By.className("dropdown-item"));
            klikNaFormulare.click();}     //void proRodice jsem vytvorila, protože podle zadání,
                                          //tam měl být ještě nějaký seznam "pro rodiče" ale nakonec
                                            //se jednalo o ten stejný seznam, co předtím a tento postup nikam
                                            //nevede, ale nechám to tady, kdyby náhodou

}






















































     /*
    @AfterEach
    public void tearDown() {
        prohlizec.quit(); */
