$(document).ready(function(){
    if($('.product-form').length == 0){
        return;
    }
    
    $('.add-product').click(function(){
        var productOptions = _productOptionsToForm(".add-product", "products")
        var productFormIndex = $('.product-form-row').length
        $('.product-form').append(_productForm(productFormIndex, productOptions));
    })
})


function _productForm(productFormIndex, productOptions) {
    var productForm = "<div class='form-row product-form-row'>";
    productForm += "<div class='form-group col-md-6'>";
    if(productFormIndex == 0) {
        productForm += "<label>Produto</label>"
    }
    productForm += "<select id='pedido_produto_" + productFormIndex + "_id' name='pedido[produto][" + productFormIndex +"][id]' class='form-control'>";
    productForm += productOptions;
    productForm += "</select>";
    productForm += "</div>";
    productForm += "<div class='form-group col-md-2'>";
    if(productFormIndex == 0) {
        productForm += "<label>Quantidade</label>"
    }
    productForm += "<input type='number' min='1' value='1' class='form-control' id='pedido_produto_" + productFormIndex + "_quantidade' name='pedido[produto][" + productFormIndex + "][quantidade]'></input>";
    productForm += "</div>"
    productForm += "</div>"
    return productForm;
}

function _productOptionsToForm(selector, dataAttribute) {
    var productOptions = "<option>Choose...</option>";
    var products = $(selector).data(dataAttribute);
    for (i = 0; i < products.length; i++) {
        productOptions += "<option value='" + products[i].id +"'>" + products[i].descricao + "</option>";
    }
    return productOptions;
}