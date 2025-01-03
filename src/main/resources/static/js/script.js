async function getCrypto() {
    const url = "/api/crypto";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}
async function getUserCrypto(){
    let userCrypto =[];
    const userAmount = await getAmount()
    for (const [id, amount] of Object.entries(userAmount)) {
        if (amount) {
            const crypto = await getCryptoById(id)
            userCrypto.push(crypto)
        }
    }
    return userCrypto
}
async function getCryptoById(id) {
    const url = "/api/crypto/id/"+id;
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}

async function getBalance() {
    const url = "/session/getBalance";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}

async function getAmount() {
    const url = "/session/getAmount";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}

async function getUserTransactions() {
    const url = "/session/getTransactions";
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
    const amountBuy = document.getElementById("amountBuy").value;
    const title = document.getElementById("title").value
    const description = document.getElementById("description").value


    const selectedSellOption = Array.from(document.getElementById("datalistSell").options).find(
        (option) => option.value === datalistSellInput
    );
    const cryptoSellId = selectedSellOption ? selectedSellOption.getAttribute("crypto_id") : null;

    const selectedBuyOption = Array.from(document.getElementById("datalistBuy").options).find(
        (option) => option.value === datalistBuyInput
    );
    const cryptoBuyId = selectedBuyOption ? selectedBuyOption.getAttribute("crypto_id") : null;

    if (!cryptoSellId || !amountSell || !cryptoBuyId || !amountBuy || !title || !description) {
        showToast("Please fill out all fields.")
        return;
    }
    console.log(cryptoBuyId)
    const data = {
        "cryptoIdSold": parseInt(cryptoSellId),
        "amountSold": parseFloat(amountSell),
        "cryptoIdBought": parseInt(cryptoBuyId),
        "amountBought": parseFloat(amountBuy),
        "title": title,
        "description": description
    };
    var response
    try {
        response = await fetch("/session/buy", {
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

async function manageAddBalance() {
    try {
        const response = await fetch("/session/addBalance", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "cryptoIdBought": 10, "amountBought": 1000}),
        });

        if (!response.ok) {
            const errorMessage = await response.json();// Or response.json() if the server returns JSON
            showToast(`Error: ${errorMessage.message}`);
        } else {
            setTimeout(function(){
                window.location.reload()
            },1500)
            showToast(`Success, your money has been added`);
        }
    } catch (error) {
        showToast(`Error: ${error.message || "An unexpected error occurred."}`);
    }
}

async function setTransactions(){
    const transactions = await getUserTransactions()
    const myList = document.querySelector('tbody')
    for (var transaction of transactions){
        var myItem = document.createElement('tr')
        var columns = ['title','description','cryptoIdSold','amountSold','cryptoIdBought','amountBought']
            for (var column of columns){
                var col = document.createElement('td')
                if (column === 'cryptoIdBought' || column === 'cryptoIdSold' ){
                    const crypto = await getCryptoById(transaction[column])
                    var img = document.createElement('img')
                    img.setAttribute('src', crypto.logoUrl)
                    img.setAttribute('class','crypto-image')
                    var a = document.createElement('a')
                    a.innerHTML = crypto.name.toUpperCase()
                    col.appendChild(img)
                    col.appendChild(a)
                }else{
                    col.innerHTML = transaction[column]
                }
                myItem.appendChild(col)
        }
        myList.appendChild(myItem)
    }
}

