package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import db.DB;
import model.dao.DaoFactory;
import model.dao.EmployeeDao;
import model.entities.Account;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Product;
import model.services.OrderService;
import model.services.PaymentService;

public class Program {

	public static Double totalPayment(List<Product> products) {
		Double sum = 0.0;
		for(Product p: products) {
			sum += (p.getPrice() * p.getQuantity());
		}
		return sum;
	}

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner scan = new Scanner(System.in);

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs_ = null;

		String email, password, emp, cargo, name, type;
		double salary, value, price;
		int op, count;
		List<Employee> employees = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM EMPLOYEE;");

			while(rs.next()) {
				employees.add(new Employee(rs.getInt("ID"),rs.getString("NOME"), new Account(rs.getString("EMAIL"), rs.getString("SENHA")),
						rs.getDouble("SALARY"), rs.getString("CARGO")));
			}
			rs_ = st.executeQuery("SELECT * FROM PRODUCT;");
			while(rs_.next()) {
				products.add(new Product(rs_.getInt("ID"), rs_.getString("NOME"), 
						rs_.getDouble("PRICE"), rs_.getString("TYPE"), null));
			}

			System.err.println("### ACESSAR CONTA ###");
			System.out.print("\nEmail: ");
			email = scan.nextLine();
			System.out.print("Senha: ");
			password = scan.nextLine();
			
			EmployeeDao employeee = DaoFactory.createEmployeeDao();
			Employee emploue = employeee.findByAccount(email, password);
			
			if(emploue == null) {
				throw new NullPointerException("Email ou senha inexistente!");
			}
			
			System.err.println("\n### TELA INICIAL ###");
			if(emploue.getCargo().equalsIgnoreCase("gerente")) {
				System.out.println("FUNCIONÁRIO: " + emploue.getName()+". CARGO: " + emploue.getCargo());
				System.out.println("\n[1] TABELA DE PREÇOS;");
				System.out.println("[2] ADICIONAR PRODUTO;");
				System.out.println("[3] DELETAR PRODUTO;");
				System.out.println("[4] ATUALIZAR PRODUTO;");
				System.out.println("[5] FUNCIONÁRIOS;");
				System.out.print("\nOpção (Digite): ");
				op = scan.nextInt();
				scan.nextLine();

				switch(op) {
				case 1:
					System.err.println("\n### TABELA DE PREÇOS ###");
					Product.readTablePrices(products);
					break;
				case 2:
					System.out.print("Produto: ");
					name = scan.nextLine();
					System.out.print("Preço: ");
					price = scan.nextDouble();
					scan.nextLine();
					System.out.print("Tipo (bebida/comida/fruta): ");
					type = scan.nextLine();
					Product.addProduct(name, price, type);
					break;
				case 3: 
					System.out.print("Produto a ser deletado: ");
					String name_ = scan.nextLine();
					List<Product> p1 = products.stream().
							filter(x -> x.getName().equalsIgnoreCase(name_)).
							collect(Collectors.toList());
					Product.deleteProduct(p1.get(0).getID());
					break;
				case 4:
					System.err.println("\n### ALTERAR PRODUTO ###");
					System.out.println();
					System.out.println("[1] ALTERAR NOME;");
					System.out.println("[2] ALTERAR PREÇO;\n");
					System.out.print("Opção (Digite): ");
					op = scan.nextInt();
					scan.nextLine();

					if(op == 1) {
						System.out.print("Produto a ser alterado: ");
						String oldName = scan.nextLine();
						System.out.print("Novo nome: ");
						String newName = scan.nextLine();
						List<Product> p2 = products.stream().
								filter(x -> x.getName().equalsIgnoreCase(oldName)).
								collect(Collectors.toList());
						Product.updateNameProduct(p2.get(0).getID(), newName);
					} else if (op == 2) {
						System.out.print("Produto a ser alterado: ");
						String nameUpdate = scan.nextLine();
						System.out.print("Novo preço: R$ ");
						Double newPrice = scan.nextDouble();
						List<Product> p2 = products.stream().
								filter(x -> x.getName().equalsIgnoreCase(nameUpdate)).
								collect(Collectors.toList());
						Product.updatePriceProduct(p2.get(0).getID(), newPrice);
					} else {
						System.err.println("Opção inválida!");
					}
					break;
				case 5:
					System.err.println("\n### GERENCIAMENTO DE FUNCIONÁRIOS ###");
					System.out.println("\n[1] ADICIONAR;");
					System.out.println("[2] ATUALIZAR SALÁRIO;");
					System.out.println("[3] DELETAR;");
					System.out.println("[4] FOLHA DE PAGAMENTO;\n");
					System.out.print("Opção (Digite): ");
					op = scan.nextInt();
					scan.nextLine();
					System.out.println();

					if (op == 1) {
						System.err.println("-> CRIAR CONTA ");
						System.out.print("\nFuncionário: ");
						emp = scan.nextLine();
						System.out.print("Email: ");
						String email_ = scan.nextLine();
						System.out.print("Senha: ");					
						String password_ = scan.nextLine();
						System.out.print("Salário: ");
						salary = scan.nextDouble();
						scan.nextLine();
						System.out.print("Cargo: ");
						cargo = scan.nextLine();

						Account.createAccount(employees, email_, password_, emp, salary, cargo);
					} else if(op == 2) {
						System.err.println("-> ATUALIZAR SALÁRIO ");
						System.out.print("\nFuncionário: ");
						emp = scan.nextLine();
						System.out.print("Salário: ");
						salary = scan.nextDouble();

						List<Employee> emp2 = employees.stream()
								.filter(x -> x.getName().equalsIgnoreCase(emp))
								.collect(Collectors.toList());

						Employee.updateEmployee(emp2.get(0).getID(), salary);
						System.out.println("SALARIO ALTERADO");
					} else if(op == 3) {
						System.err.println("-> DELETAR FUNCIONÁRIO ");
						System.out.print("\nFuncionário: ");
						emp = scan.nextLine();
						
						List<Employee> emp2 = employees.stream()
								.filter(x -> x.getName().equalsIgnoreCase(emp))
								.collect(Collectors.toList());
						
						System.out.print("\nDigite CONFIRMAR para deletar "+emp2.get(0).getName()+": ");
						String conf = scan.nextLine();
						
						if(conf.equals("CONFIRMAR")) {
							Employee.deleteEmployee(emp2.get(0).getID());
							System.out.println("FUNCIONÁRIO DELETADO!");
						} else {
							System.out.println("CANCELADO.");
						}
					} else if(op == 4) {
						System.err.println("FUNCIONÁRIOS: ");
						employees.forEach(x -> System.out.println("-> " + x.getName()+" ("+x.getCargo().toLowerCase()+")"+", salário: R$ " +
						String.format("%.2f", x.getSalary())+";"));
						System.out.println();
						System.out.println("TOTAL: R$ " + String.format("%.2f", Employee.payroll(employees)));
					}else {
						System.out.println("Opção inválida!");
					}
					break;
				default:
					System.err.println("Opção inválida!");
					break;
				}

			} else if (emploue.getCargo().equalsIgnoreCase("atendente")) {
				System.out.println("FUNCIONÁRIO: " + emploue.getName()+". CARGO: " + emploue.getCargo());
				System.out.println("\n[1] TABELA DE PREÇOS;");
				System.out.println("[2] ADICIONAR COMPRA;");
				System.out.print("\nOpção (Digite): ");
				op = scan.nextInt();
				scan.nextLine();
				
				switch(op) {
				case 1:
					System.err.println("\n### TABELA DE PREÇOS ###");
					Product.readTablePrices(products);
					break;
				case 2:
					System.err.println("\n### ADICIONAR COMPRA ###");
					List<Product> shopping = new ArrayList<>();
					count = 1;

					for(int i = 0;i < count;i++) {
						System.out.print("\nProduto: ");
						String product_ = scan.nextLine();
						List<Product> p = products.stream()
						        .filter(x -> x.getName().equalsIgnoreCase(product_))
								.collect(Collectors.toList());
						if(p.isEmpty() == true) {
							System.err.println("Produto inexistente.\n");
						} 
						p.forEach(System.out::println);
						System.out.print("Quantidade: ");
						Integer qnt = scan.nextInt();

						p.forEach(x -> shopping.add(new Product(null, x.getName(), x.getPrice(), x.getType(), qnt)));

						System.out.print("\nDeseja adicionar mais (y/n)? ");
						char resp = scan.next().charAt(0);

						if(resp == 'y') {
							count++;
						} else {
							count = 0;
						}
						scan.nextLine();
					}
					do {
						System.out.println("\nTotal: R$ " + String.format("%.2f", Program.totalPayment(shopping)));
						System.out.print("Valor: ");
						value = scan.nextDouble();

						if(Program.totalPayment(shopping) > value) {
							System.err.println("Valor baixo!\n");
						}
					} while(Program.totalPayment(shopping) > value);

					PaymentService payment = new PaymentService(shopping, value);
					Order order = new Order(shopping, new Date(), new OrderService(payment));

					System.err.println("\n-> DETALHES DA COMPRA:");
					order.readOrder(shopping);
					break;
				default:
					System.err.println("Opção inválida!");
					break;
				}
			}
		}
		catch (InputMismatchException e) {
			System.err.println("Digite apenas números inteiros!");
		}
		catch(SQLException e) {
			System.err.println("Erro no Banco de Dados!");
		}
		catch(NullPointerException e) {
			System.err.println(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeResultSet(rs_);
			DB.closeConnection();
			scan.close();
		}
	}
}
