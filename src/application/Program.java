package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String arquivo = sc.nextLine();

        List<Sale> lista = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                lista.add(new Sale(month, year, seller, items, total ));
                line = br.readLine();
            }

            System.out.println();
            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");

            List<Sale> vendas2016 = lista.stream()
                    .filter(s -> s.getYear() == 2016)
                    .sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
                    .limit(5)
                    .collect(Collectors.toList());

            vendas2016.forEach(System.out::println);

            Double totalLogan = lista.stream()
                    .filter(s -> s.getSeller().equals("Logan"))
                    .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                    .mapToDouble(Sale::getTotal)
                    .sum();

            System.out.printf("%nValor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", totalLogan);

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }
}
