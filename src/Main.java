import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Usuario {
    String nome;
    String email;
    String senha;
    List<Carro> carrosReservados = new ArrayList<>();

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}

class Carro {
    String modelo;
    String placa;
    boolean reservado;

    public Carro(String modelo, String placa) {
        this.modelo = modelo;
        this.placa = placa;
        this.reservado = false;
    }
}

public class Main {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Carro> carros = new ArrayList<>();
    private static Usuario usuarioLogado = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("=======================================================");
            System.out.println("1 - Cadastrar Usuário 🙋‍♂️");
            System.out.println("2 - Login 🔐");
            System.out.println("3 - Cadastrar Carro 🚗");
            System.out.println("4 - Reservar Carro 🚗");
            System.out.println("5 - Ver Carros Reservados 🚗");
            System.out.println("6 - Sair 🖐️");
            System.out.println("=======================================================");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir nova linha

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> login();
                case 3 -> cadastrarCarro();
                case 4 -> reservarCarro();
                case 5 -> verCarrosReservados();
                case 6 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 6);
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        usuarios.add(new Usuario(nome, email, senha));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        usuarioLogado = usuarios.stream()
                .filter(u -> u.email.equals(email) && u.senha.equals(senha))
                .findFirst().orElse(null);
        if (usuarioLogado != null) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    private static void cadastrarCarro() {
        if (verificarLogin()) return;
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        carros.add(new Carro(modelo, placa));
        System.out.println("Carro cadastrado com sucesso!");
    }

    private static void reservarCarro() {
        if (verificarLogin()) return;
        System.out.println("Carros disponíveis:");
        List<Carro> disponiveis = carros.stream().filter(c -> !c.reservado).toList();
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum carro disponível para reserva.");
            return;
        }

        for (int i = 0; i < disponiveis.size(); i++) {
            System.out.printf("%d. Modelo: %s | Placa: %s%n", i + 1, disponiveis.get(i).modelo, disponiveis.get(i).placa);
        }
        System.out.print("Escolha o número do carro para reservar: ");
        int escolha = scanner.nextInt() - 1;
        if (escolha >= 0 && escolha < disponiveis.size()) {
            Carro carroSelecionado = disponiveis.get(escolha);
            carroSelecionado.reservado = true;
            usuarioLogado.carrosReservados.add(carroSelecionado);
            System.out.println("Carro reservado com sucesso!");
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    private static void verCarrosReservados() {
        if (verificarLogin()) return;
        if (usuarioLogado.carrosReservados.isEmpty()) {
            System.out.println("Nenhum carro reservado.");
        } else {
            System.out.println("Carros reservados por você:");
            usuarioLogado.carrosReservados.forEach(carro ->
                    System.out.printf("Modelo: %s | Placa: %s%n", carro.modelo, carro.placa));
        }
    }

    private static boolean verificarLogin() {
        if (usuarioLogado == null) {
            System.out.println("É necessário estar logado para realizar essa operação.");
            return true;
        }
        return false;
    }
}
