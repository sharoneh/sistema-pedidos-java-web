package br.com.pedidos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.pedidos.models.Cliente;
import br.com.pedidos.models.ItemDoPedido;
import br.com.pedidos.models.Pedido;
import br.com.pedidos.models.Produto;
import java.util.Date;

/**
 * Created by Regis Gaboardi (@gmail.com)
 * Provided with Love and IntelliJ IDEA for SistemaDePedidosWeb.
 * 09/10/2020
 */

public class PedidoDAO {

    private Connection connection;

    /**
     * Cria um Pedido para um Cliente.
     * @param clienteID
     * @return
     * @throws SQLException
     */
    public Pedido createPedido(int clienteID) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        Pedido result = findPedido(clienteID, false);
        if (result.getData() != null) {
            return result;
        }
        String insert = "INSERT INTO pedido (data, cliente_fk) VALUES (NOW(), ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setInt(1, clienteID);
            ps.execute();
            Pedido p = findPedido(clienteID, true);
            return p;
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getMessage());
        } finally {
            this.connection.close();
        }
    }

    /**
     * Retorna o Pedido simples (ID, data) de um Cliente.
     * @param clienteID
     * @param closeConnection
     * @return
     * @throws SQLException
     */
    private Pedido findPedido(int clienteID, boolean closeConnection) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        Pedido result = new Pedido();
        String find = "SELECT id, data FROM pedido WHERE cliente_fk = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(find);
            ps.setInt(1, clienteID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result.setId(rs.getInt("id"));
                result.setData(rs.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (closeConnection) {
                this.connection.close();
            }
        }
        return result;
    }


    /**
     * Retorna o Pedido completo (Pedido, Cliente, ItensDoPedido (Produto, Quantidade)) de um Cliente.
     * @param clientID
     * @return
     * @throws SQLException
     */
    public List<ItemDoPedido> findItensDoCliente(int clientID) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        List<ItemDoPedido> resultList = new ArrayList<>();
        String findItens = "SELECT prd.id AS proid, prd.descricao AS prodesc, pp.quantidade as quantidade " +
                "FROM produto_pedido pp " +
                "JOIN produto prd ON prd.id = pp.produto_fk " +
                "JOIN pedido pdd ON pp.pedido_fk = pdd.id " +
                "WHERE pdd.cliente_fk = ?";
        try {
            PreparedStatement itensStatement = connection.prepareStatement(findItens);
            itensStatement.setInt(1, clientID);
            ResultSet rs = itensStatement.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("proid"));
                produto.setDescricao(rs.getString("prodesc"));
                ItemDoPedido item = new ItemDoPedido();
                item.setQuantidade(Integer.valueOf(rs.getString("quantidade")));
                item.setProduto(produto);
                resultList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Adiciona um ItemDoPedido a um Pedido.
     * @param item
     * @param pedido
     * @throws SQLException
     */
    public void saveItem(ItemDoPedido item, int pedido) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        String add = "INSERT INTO produto_pedido (produto_fk, pedido_fk, quantidade) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(add);
            ps.setInt(1, item.getProduto().getId());
            ps.setInt(2, pedido);
            ps.setInt(3, item.getQuantidade());

            ps.execute();
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getMessage());
        } finally {
            this.connection.close();
        }
    }


    public void atualizaQuantidade(ItemDoPedido item, int cliente) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        String update = "UPDATE produto_pedido pp " +
                "JOIN produto pdt ON pp.produto_fk = pdt.id " +
                "JOIN pedido pdd ON pp.pedido_fk = pdd.id " +
                "SET pp.quantidade = ? " +
                "WHERE pdt.descricao = ? " +
                "AND pdd.cliente_fk = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(update);
            ps.setInt(1, item.getQuantidade());
            ps.setString(2, item.getProduto().getDescricao());
            ps.setInt(3, cliente);

            ps.execute();
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getMessage());
        } finally {
            this.connection.close();
        }
    }

    /**
     * Varre todos os Pedidos e DELETA todos os pedidos em que a quantidade de
     * TODOS os seus itens é menor ou igual a ZERO.
     * @param cliente
     * @throws SQLException
     */
    public void deleteGarbage(int cliente) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        String delete = "DELETE pp FROM produto_pedido pp " +
                "JOIN pedido pdd ON pp.pedido_fk = pdd.id " +
                "WHERE 0 >= pp.quantidade " +
                "AND pdd.cliente_fk = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(delete);
            ps.setInt(1, cliente);

            ps.execute();
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getMessage());
        } finally {
            this.connection.close();
        }
    }

    /**
     * Retorna lista com todos os Pedidos (completos).
     * @return
     * @throws SQLException
     */
    public List<Pedido> fetchAll() throws Exception {
        this.connection = new ConnectionFactory().getConnection();
        
        String findAll = ""
                + "SELECT "
                    + "p.id as pid, "
                    + "p.data AS data, "
                    + "c.id AS clienteId, "
                    + "c.nome as nomeCliente, "
                    + "c.sobrenome as sobrenome, "
                    + "c.cpf as cpf,"
                    + "SUM(pp.quantidade) as quantidade "
                + "FROM pedido p "
                + "JOIN cliente c ON p.cliente_fk = c.id "
                + "JOIN produto_pedido pp on p.id = pp.pedido_fk "
                + "JOIN produto prd on pp.produto_fk = prd.id "
                + "GROUP BY pid";
        try {
            PreparedStatement itensStatement = connection.prepareStatement(findAll);
            
            ResultSet rs = itensStatement.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            
            while (rs.next()) {
            	Cliente cliente = new Cliente();
            	
            	cliente.setId(rs.getInt("clienteId"));
            	cliente.setNome(rs.getString("nomeCliente"));
            	cliente.setSobrenome(rs.getString("sobrenome"));
            	cliente.setCpf(rs.getString("cpf"));

            	ItemDoPedido item = new ItemDoPedido();
            	item.setQuantidade(rs.getInt("quantidade"));

            	List<ItemDoPedido > itens = new ArrayList<>();
            	itens.add(item);
            	
            	Pedido pedido = new Pedido();
            		
            	pedido.setData(rs.getDate("data"));
                pedido.setId(rs.getInt("pid"));
            	
            	pedido.setItens(itens);
            	pedido.setCliente(cliente);
            	
            	pedidos.add(pedido);
            	
            }
            
            return pedidos;
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception();
        }
        
    }

    /**
     * Retorna o Pedido completo (Pedido, Cliente, ItensDoPedido (Produto, Quantidade)).
     * @param id
     * @return
     * @throws SQLException
     */
    public Pedido find(int id) throws SQLException {
        this.connection = new ConnectionFactory().getConnection();
        Pedido result = new Pedido();
        String findOne = "SELECT "
                + "p.data AS data," +
                "c.id AS cid, c.nome, c.sobrenome, c.cpf," +
                "prd.id AS proid, prd.descricao AS prodesc," +
                "pp.quantidade " +
                "FROM pedido p " +
                "JOIN cliente c ON p.cliente_fk = c.id " +
                "JOIN produto_pedido pp on p.id = pp.pedido_fk " +
                "JOIN produto prd on pp.produto_fk = prd.id " +
                "WHERE p.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(findOne,
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Cliente c = new Cliente();
            List<ItemDoPedido> itens = new ArrayList<>();
            if (rs.next()) {
                c.setId(rs.getInt("cid"));
                c.setNome(rs.getString("nome"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setCpf(rs.getString("cpf"));
            }
            rs.beforeFirst();
            Date d = new Date();
            while (rs.next()) {
                Produto p = new Produto();
                p.setDescricao(rs.getString("prodesc"));
                p.setId(rs.getInt("proid"));

                ItemDoPedido each = new ItemDoPedido();
                each.setProduto(p);
                each.setQuantidade(rs.getInt("quantidade"));
                itens.add(each);
                d = rs.getDate("data");
            }
            result.setId(id);
            result.setData(d);
            result.setCliente(c);
            result.setItens(itens);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}