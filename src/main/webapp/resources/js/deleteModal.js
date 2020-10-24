$(document).ready(function(){
    var btnsDeleteProduct = $('.delete-product')
    var btnsDeleteClient = $('.delete-client')
    if(btnsDeleteProduct.length == 0 && btnsDeleteClient.length == 0){
        return;
    }
    
    btnsDeleteProduct.each(function() {
        $(this).click(function(){
            var idProduto = $('input[id$="id-produto"]');
            var descricaoProduto = $('.descricao-produto');
            idProduto.val($(this).data('id'));
            descricaoProduto.text($(this).data('descricao')); 
        });
    });
    
    btnsDeleteClient.each(function() {
        $(this).click(function(){
            var idCliente = $('input[id$="id-cliente"]');
            var nomeCliente = $('.nome-cliente');
            idCliente.val($(this).data('id'));
            nomeCliente.text($(this).data('nome')); 
        });
    });
})


