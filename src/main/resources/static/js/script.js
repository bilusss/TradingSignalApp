async function getCrypto() {
    const url = "/api/crypto";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}
async function getCryptoById(id) {
    const url = "/api/crypto/"+id;
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}

async function getBalance() {
    const url = "/api/transactions/balance";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}

async function getAmount() {
    const url = "/api/transactions/amount";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}
function showToast (message){
    const liveToast = document.getElementById('liveToast')
    const toastBody = liveToast.querySelector(".toast-body");
    toastBody.innerHTML=message
    const toastBootstrap = new bootstrap.Toast(liveToast);
    toastBootstrap.show()
}

function addDatalist(datalist_id,cryptos){
    datalist = document.getElementById(datalist_id)
    for (var crypto of cryptos){
        const option = document.createElement('option')
        option.setAttribute('value', crypto.name)
        option.setAttribute('crypto_id', crypto.id)
        datalist.appendChild(option)
    }
}

async function manageTransactionForm() {
    // Get form values
    const datalistSellInput = document.getElementById("datalistSellInput").value;
    const amountSell = document.getElementById("amountSell").value;
    const datalistBuyInput = document.getElementById("datalistBuyInput").value;

    const selectedSellOption = Array.from(document.getElementById("datalistSell").options).find(
        (option) => option.value === datalistSellInput
    );
    const cryptoSellId = selectedSellOption ? selectedSellOption.getAttribute("crypto_id") : null;

    const selectedBuyOption = Array.from(document.getElementById("datalistSell").options).find(
        (option) => option.value === datalistBuyInput
    );
    const cryptoBuyId = selectedBuyOption ? selectedBuyOption.getAttribute("crypto_id") : null;

    if (!cryptoSellId || !amountSell || !datalistBuyInput) {
        showToast("Please fill out all fields.")
        return;
    }

    const data = {
        cryptoSellId: parseInt(cryptoSellId),
        amount: parseFloat(amountSell),
        cryptoBuyId: parseInt(cryptoBuyId),
    };
    console.log(data)
    var response
    try {
        response = await fetch("/api/transactions/buy", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
    } catch (error) {
        showToast("Error sending transaction:" + error.body)
    }

    if (response.ok) {
        showToast("Success") // Response message can be changed
    } else {
        showToast("Error:" + response.statusText) // Response message can be changed
    }
}