import java.io.FileWriter;
import java.io.IOException;
public class Declaracionimpuestos1 {
    public static void main(String[] args) {
        int SueldosAño = 12, numeroCategoria = 6;
        double PorcentajeDeduccion[] = {0.2, 0.35, 0.4, 0.1, 0.4, 0.1};
        double Impuesto[] = {0, 0, 163, 615, 1377, 2611, 4841, 8602, 14648, 23956};
        double FranccionBasica[] = {11902, 15159, 19682, 26031, 34255, 45407, 60450, 80605, 107199};
        double Tasa[] = {0, 0.05, 0.1, 0.12, 0.15, 0.2, 0.25, 0.3, 0.35, 0.37};
        String Meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",  "Octubre", "Noviembre", "Diciembre"};
        String Categorias[] = {"Vivienda", "Educacion", "Alimentacion", "Vestimenta", "Salud", "Turismo"};
        double SueldoMensual[] = new double[SueldosAño];
        double DineroCategorias[] = new double[numeroCategoria];
        SueldosMensuales(SueldoMensual, SueldosAño);
        double totalCategorias = GenerarFacturas(DineroCategorias, Categorias, Meses, SueldosAño);
        guardarSueldosEnCSV(SueldoMensual, Meses);
        CalculoImpuestos(DineroCategorias, SueldoMensual, numeroCategoria, SueldosAño, FranccionBasica, Tasa, Impuesto, totalCategorias);
    }
    public static void SueldosMensuales(double SueldoMensual[], int SueldosAño) {
        for (int i = 0; i < SueldosAño; i++) {
            SueldoMensual[i] = (int) (Math.random() * (2000 - 0 + 1) + 900);
        }
    }
    public static double GenerarFacturas(double DineroCategorias[], String Categorias[], String Meses[], int SueldosAño) {
        double totalCategorias = 0;
        try (FileWriter csvWriter = new FileWriter("facturas.csv")) {
            for (int i = 0; i < SueldosAño; i++) {
                csvWriter.append("------------Facturas de " + Meses[i] + "------------\n");
                int Facturas = (int) (Math.random() * (10 - 0 + 1) + 3);
                int Total = 0;
                for (int j = 0; j < Facturas; j++) {
                    int Valor1 = (int) (Math.random() * (40 - 0 + 1) + 9);
                    Total = Total + Valor1;
                    int l = (int) (Math.random() * (5 - 0 + 1));
                    csvWriter.append(Categorias[l] + ": " + Valor1 + "\n");
                    DineroCategorias[l] = DineroCategorias[l] + Valor1;
                }
                csvWriter.append("Total Facturas: " + Total + "\n\n");
            }
            totalCategorias = TablaGastos(DineroCategorias, Categorias, SueldosAño);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalCategorias;
    }
    public static void guardarSueldosEnCSV(double SueldoMensual[], String Meses[]) {
        try (FileWriter csvWriter = new FileWriter("sueldos.csv")) {
            csvWriter.append("Mes,Sueldo\n");
            for (int i = 0; i < SueldoMensual.length; i++) {
                csvWriter.append(Meses[i] + "," + SueldoMensual[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void MostrarSueldosMensuales(String Meses[], double SueldoMensual[], int SueldosAño) {
        double Total = 0;
        System.out.println("---------Sueldos Mensuales-----------");
        System.out.print("Meses   |   ");
        for (int i = 0; i < SueldosAño; i++) {
            System.out.print(Meses[i] + "  |  ");
        }
        System.out.println("");
        System.out.print("Sueldos   |   ");
        for (int i = 0; i < SueldosAño; i++) {
            System.out.print(SueldoMensual[i] + "  |  ");
            Total = Total + SueldoMensual[i];
        }
        System.out.println("");
        System.out.println("Total: " + Total);
        System.out.println("");
    }

    public static double TablaGastos(double DineroCategorias[], String Categorias[], int SueldosAño) {
        double Total = 0;
        try (FileWriter csvWriter = new FileWriter("facturas.csv", true)) {
            csvWriter.append("---------Total de las Categorias-----------\n");
            System.out.print("Categorias   |   ");
            for (int i = 0; i < DineroCategorias.length; i++) {
                System.out.print(Categorias[i] + "  |  ");
                csvWriter.append(Categorias[i] + "  |  ");
            }
            System.out.println("");
            csvWriter.append("\n");
            System.out.print("Total   |   ");
            for (int i = 0; i < DineroCategorias.length; i++) {
                System.out.print(DineroCategorias[i] + "  |  ");
                csvWriter.append(DineroCategorias[i] + "  |  ");
                Total = Total + DineroCategorias[i];
            }
            System.out.println("");
            csvWriter.append("\n");
            System.out.println("Total: " + Total);
            csvWriter.append("Total: " + Total + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Total;
    }
    public static void CalculoImpuestos(double DineroCategorias[], double SueldoMensual[], int numeroCategoria,
        int SueldosAño, double FranccionBasica[], double Tasa[], double Impuesto[], double totalCategorias) {
        double Maximo = 5327;
        double SueldoAnual = 0;
        double Deduccion = totalCategorias;
        StringBuilder resultados = new StringBuilder();

        resultados.append("---------Calculo de Impuestos-----------\n");
        for (int i = 0; i < SueldosAño; i++) {
            SueldoAnual = SueldoAnual + SueldoMensual[i];
        }
        resultados.append("Total: " + SueldoAnual + "\n");

        if (Deduccion > Maximo) {
            Deduccion = Maximo;
        }
        resultados.append("Total deducciones por facturas: " + Deduccion + "\n");
        resultados.append("Formula=((SueldoAnual-Deducciones)*Tasa)+FraccionBasica\n");
        resultados.append("Impuestos totales a pagar: " + Formula(Deduccion, SueldoAnual, FranccionBasica, Tasa, Impuesto) + "\n");
        try (FileWriter csvWriter = new FileWriter("facturas.csv", true)) {
            csvWriter.append(resultados.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double Formula(double Deduccion, double SueldoAnual, double FranccionBasica[], double Tasa[],
                                 double Impuesto[]) {
        double DevolverFormula = 0;
        boolean Continuar = true;
        DevolverFormula = SueldoAnual - Deduccion;
        int i = 1;
        while (Continuar) {
            if (DevolverFormula < FranccionBasica[i]) {
                DevolverFormula = (DevolverFormula * Tasa[i]) + Impuesto[i];
                Continuar = false;
            } else
                i = i + 1;
        }
        return DevolverFormula;
    }
}
   
/*Mes,Sueldo
Enero,2124.0
Febrero,1380.0
Marzo,1545.0
Abril,1942.0
Mayo,2624.0
Junio,1825.0
Julio,1536.0
Agosto,1356.0
Septiembre,2149.0
Octubre,1622.0
Noviembre,1655.0
Diciembre,1280.0

------------Facturas de Enero------------
Vestimenta: 12
Turismo: 10
Vestimenta: 35
Educacion: 11
Salud: 32
Salud: 37
Alimentacion: 37
Total Facturas: 174

------------Facturas de Febrero------------
Alimentacion: 20
Vivienda: 14
Salud: 31
Salud: 32
Salud: 32
Turismo: 43
Salud: 20
Alimentacion: 14
Total Facturas: 206

------------Facturas de Marzo------------
Educacion: 24
Educacion: 37
Educacion: 49
Alimentacion: 43
Turismo: 36
Vivienda: 14
Turismo: 48
Turismo: 37
Vivienda: 9
Educacion: 47
Alimentacion: 47
Vivienda: 42
Turismo: 47
Total Facturas: 480

------------Facturas de Abril------------
Vivienda: 48
Alimentacion: 40
Educacion: 28
Alimentacion: 34
Vivienda: 41
Salud: 37
Salud: 29
Turismo: 38
Total Facturas: 295

------------Facturas de Mayo------------
Vestimenta: 11
Alimentacion: 20
Educacion: 31
Turismo: 43
Vivienda: 43
Alimentacion: 28
Alimentacion: 21
Salud: 17
Total Facturas: 214

------------Facturas de Junio------------
Educacion: 17
Turismo: 39
Salud: 49
Total Facturas: 105

------------Facturas de Julio------------
Alimentacion: 27
Turismo: 20
Alimentacion: 31
Vivienda: 17
Salud: 26
Total Facturas: 121

------------Facturas de Agosto------------
Vivienda: 23
Vivienda: 40
Educacion: 25
Alimentacion: 26
Vivienda: 15
Turismo: 38
Total Facturas: 167

------------Facturas de Septiembre------------
Vestimenta: 11
Vivienda: 16
Vivienda: 33
Vivienda: 19
Alimentacion: 25
Alimentacion: 41
Vestimenta: 35
Educacion: 12
Salud: 39
Alimentacion: 41
Total Facturas: 272

------------Facturas de Octubre------------
Educacion: 41
Turismo: 41
Educacion: 14
Turismo: 34
Educacion: 39
Turismo: 12
Alimentacion: 40
Total Facturas: 221

------------Facturas de Noviembre------------
Salud: 17
Vivienda: 36
Vestimenta: 12
Salud: 30
Alimentacion: 21
Educacion: 34
Total Facturas: 150

------------Facturas de Diciembre------------
Vivienda: 20
Alimentacion: 38
Alimentacion: 44
Vestimenta: 26
Total Facturas: 128

---------Calculo de Impuestos-----------
Total: 21038.0
Total deducciones por facturas: 2533.0
Formula=((SueldoAnual-Deducciones)*Tasa)+FraccionBasica
Impuestos totales a pagar: 2013.5

Categorias   |   Vivienda  |  Educacion  |  Alimentacion  |  Vestimenta  |  Salud  |  Turismo  |  
Total   |   430.0  |  409.0  |  638.0  |  142.0  |  428.0  |  486.0  |  
Total: 2533.0
