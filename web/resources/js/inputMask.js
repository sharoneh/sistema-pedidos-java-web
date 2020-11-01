$(document).ready(function(){
  var cpfCliente = $('.cpf-cliente');
  
  if(cpfCliente.length == 0) {
      return;
  }
  
  cpfCliente.mask('000.000.000-00');
});
