<script>


    addItemToCart = function (id) {
        $.post("${pageContext.request.contextPath}/cart/addproduct",
            {
                id: id,
            },
            function (data, status) {
                updateCartCounter();
                console.log(status);
            });
    }
    updateCartCounter = function () {
        console.log("called");
        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/cart/getproductscount",
            success: function (data) {
                $("#cartCounter").text(data.count);
            }
        });

    }
    updateButtonStyle = function (buttonId) {
        $("#" + buttonId).removeClass("btn-primary");
        $("#" + buttonId).removeClass("btn-outline-primary");
        $("#" + buttonId).addClass("btn-secondary");
    }

    $(document).ready(function () {
        updateCartCounter();
    })

    changeAmount = function (id) {

        let amount = $("#amountInput-" + id).val();
        amount = Number(amount);
        if (amount <= 0 || amount > 20) {
            $("#amountInput-" + id).addClass("text-danger");
        } else {

            $.post("${pageContext.request.contextPath}/cart/setproductscount",
                {
                    id: id,
                    amount: $("#amountInput-" + id).val()
                },
                function (data, status) {
                    updateCartCounter();
                    window.location.reload();
                });
        }


    }

    deleteFromCart = function (id){
        $.post("${pageContext.request.contextPath}/cart/setproductscount",
            {
                id: id,
                amount: 0
            },
            function (data, status) {
                updateCartCounter();
                window.location.reload();
            });
    }

    refreshWindow = function () {
        window.location.reload();
    }

</script>