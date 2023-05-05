
<script>


addItemToCart = function (id) {
    $.post("${pageContext.request.contextPath}/cart/addproduct",
        {
            id: id,
        },
        function(data, status){
            updateCartCounter();
            console.log(status);
        });
}
updateCartCounter = function (){
    console.log("called");
    $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/cart/getproductscount",
        success: function( data ) {
            $("#cartCounter").text(data.count);
        }
    });

}
updateButtonStyle = function (buttonId){
    $("#"+buttonId).removeClass("btn-primary");
    $("#"+buttonId).removeClass("btn-outline-primary");
    $("#"+buttonId).addClass("btn-secondary");
}

$(document).ready(function() {
    updateCartCounter();
})

</script>