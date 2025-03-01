function crypto_search(event) {
    event.preventDefault();
    const query = document.getElementById('cryptoInput').value;
    if (query) {
        // Redirect to a specific URL or handle the input as needed
        window.location.href = `/chart/${query.toUpperCase()}USDT`;
    } else {
        alert("Please enter a cryptocurrency name!"); // Optional validation
    }
}

function fill_register(event) {
    event.preventDefault();
    const query = document.getElementById('newsletterEmail').value;
    window.location.href = `/register?email=${query}`;
    // TODO add to mailing list in future

}

/**
 *
 * Code for fetching data from database
 *
 */

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

async function getNetworth() {
    const url = "/session/getNetworth";
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


/**
 *
 * Code for displaying data on the website
 *
 */
async function setTrade () {
    const myList = document.querySelector("tbody")
    const cryptoList = await getCrypto()
    for (var crypto of cryptoList) {
        var myItem = document.createElement('tr')
        let button
        if (crypto.symbol === "USDT") {
            button = `<td><a href="/chart/USDTPLN" style="text-decoration: none;"><button class="btn btn-outline-success btn-sm btn-block">Buy/sell</button></a></td>`
        } else {
            button = `<td><a href="/chart/${crypto['symbol']}USDT" style="text-decoration: none;"><button class="btn btn-outline-success btn-sm btn-block">Buy/sell</button></a></td>`
        }
        var columns = ['name', 'symbol', 'description']
        for (var column of columns) {
            var col = document.createElement('td')
            if (column === 'name') {
                var img = document.createElement('img')
                img.setAttribute('src', crypto.logoUrl)
                img.setAttribute('class', 'crypto-image')
                var a = document.createElement('a')
                a.innerHTML = crypto[column].toUpperCase()
                col.appendChild(img)
                col.appendChild(a)
            } else {
                col.innerHTML = crypto[column]
            }
            myItem.appendChild(col)
        }
        myItem.innerHTML += button
        myList.appendChild(myItem)
    }
}

async function setBalance(){
    await displayNetworth();
    userAmount = await getAmount()
    userBalance = await getBalance()
    for (const [id, amount] of Object.entries(userAmount)) {
        if (amount){
            const crypto = await getCryptoById(id)
            createBalanceCard(crypto.name, crypto.symbol, amount, userBalance[id])
        }
    }
}
function roundToMillionth(value) {
    return Math.round(value * 1000000) / 1000000;
}
function createBalanceCard(currencyName, currencySymbol, amount, price){
    const container = document.querySelector('#card-container')
    const tradeLink = currencySymbol === 'USDT'
        ? `/chart/${currencySymbol}PLN`
        : `/chart/${currencySymbol}USDT`;
    const formattedPrice = price.toFixed(2);
    const formattedAmount = roundToMillionth(amount);
    const cardHTML = `
        <div class="col-12 col-sm-6 col-md-3 mb-4">
            <div class="card bg-dark text-custom-green">
                <div class="card-body">
                    <h5 class="card-title">${currencyName}</h5>
                    <h6 class="card-subtitle mb-2 text-custom-dark-green">${currencySymbol}</h6>
                    <div class="d-flex"><b><p class="card-text mr-2">AMOUNT: </p></b><p class="card-text text-white">${formattedAmount}</p></div>
                    <div class="d-flex"><b><p class="card-text mr-2">VALUE: </p></b><p class="card-tex text-white">${formattedPrice}$</p></div>
                    <div class="my-2">
                        <a href="${tradeLink}" class="card-link text-center text-custom-green" style="font-size: 1.2rem;">TRADE</a>
                    </div>
                </div>
            </div>
        </div>
        `;
    container.innerHTML += cardHTML;
}

function addDatalist(datalist_id, cryptos) {
    const datalist = document.getElementById(datalist_id);
    if (!datalist) {
        return;
    }

    for (var crypto of cryptos) {
        const option = document.createElement('option');
        option.setAttribute('value', crypto.name);
        option.setAttribute('crypto_id', crypto.id);
        datalist.appendChild(option);
    }
}

async function setTransactions(){
    const transactions = await getUserTransactions()
    const myList = document.querySelector('tbody')
    for (var transaction of transactions){
        var myItem = document.createElement('tr')
        var columns = ['title','description','cryptoIdSold','amountSold','exchangeRate','cryptoIdBought','amountBought']
            for (var column of columns) {
                var col = document.createElement('td')
                if (column === 'amountSold' && transaction.description === "Adding balance"){
                    col.innerHTML = ""
                }else if (column === 'cryptoIdSold'){
                    if (transaction.title === "Adding balance" && transaction.description === "Adding balance"){
                        col.innerHTML = ""
                    } else {
                        let crypto = await getCryptoById(transaction[column])
                        var img = document.createElement('img')
                        img.setAttribute('src', crypto.logoUrl)
                        img.setAttribute('class','crypto-image')
                        var a = document.createElement('a')
                        a.innerHTML = crypto.name.toUpperCase()
                        col.appendChild(img)
                        col.appendChild(a)
                    }
                }else if (column === 'cryptoIdBought'){
                    let crypto = await getCryptoById(transaction[column])
                    var img = document.createElement('img')
                    img.setAttribute('src', crypto.logoUrl)
                    img.setAttribute('class','crypto-image')
                    var a = document.createElement('a')
                    a.innerHTML = crypto.name.toUpperCase()
                    col.appendChild(img)
                    col.appendChild(a)
                }else if (column === 'exchangeRate') {
                    var exchangeRate = parseFloat(parseFloat(transaction["amountSold"]) / parseFloat(transaction["amountBought"]))
                    exchangeRateString = String(exchangeRate.toPrecision(7))
                    if (exchangeRate === 0){
                        col.innerHTML = ""
                    }else if (exchangeRateString.includes("e")){
                        firstPart = exchangeRateString.substring(0, exchangeRateString.indexOf("e"))
                        firstPart = firstPart.substring(0, 5)
                        secondPart = exchangeRateString.substring(exchangeRateString.indexOf("e"), exchangeRateString.length)
                        col.innerHTML = firstPart+secondPart
                    }else if (exchangeRate < 0.001) {
                        var count = 0
                        var flag = true
                        var rest = ""
                        for (let i = 2; i < exchangeRateString.length; i++) {
                            if (flag === true && exchangeRateString.charAt(i) === "0") {
                                count++
                            } else {
                                flag = false
                            }
                            if (flag === false) {
                                rest += exchangeRateString.charAt(i)
                            }
                        }
                        if (count > 3) {
                            var exchangeRateShorten = "0.(" + count + ")" + rest
                            col.innerHTML = exchangeRateShorten.substring(0, 7)
                        } else {
                            var exchangeRateShorten = "0." + count * "0" + rest
                            col.innerHTML = exchangeRateShorten.substring(0, 7)
                        }
                    }else{
                        col.innerHTML = exchangeRateString.substring(0,8)
                    }
                }else{
                    col.innerHTML = transaction[column]
                }
                myItem.appendChild(col)
        }
        myList.appendChild(myItem)
    }
}

/**
 *
 * Code for managing forms
 *
 */

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
    const data = {
        "cryptoIdSold": parseInt(cryptoSellId),
        "amountSold": parseFloat(amountSell),
        "cryptoIdBought": parseInt(cryptoBuyId),
        "amountBought": parseFloat(amountBuy),
        "echangeRate": parseFloat(amountSell/amountBuy),
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
        showToast("Transaction successful!");
    } else {
        try {
            const errorData = await response.json();
            if (errorData && errorData.message) {
                showToast("Error:" + errorData.message);
            } else {
                showToast("An unexpected error occurred.");
            }
        } catch (e) {
            showToast("An unexpected error occurred while parsing the error response.");
        }
    }
}

async function manageAddBalance() {
    const datalistAddInput = document.getElementById("datalistAddInput").value;
    const selectedAddOption = Array.from(document.getElementById("datalistAdd").options).find(
        (option) => option.value === datalistAddInput
    );
    const amountAdd = document.getElementById("amountBuy").value;
    const cryptoAddId = selectedAddOption ? selectedAddOption.getAttribute("crypto_id") : null;
    if (amountAdd<=0) {
        showToast("Please insert position number.")
        return;
    }
    if (!cryptoAddId || !amountAdd) {
        showToast("Please fill out all fields.")
        return;
    }
    try {
        const response = await fetch("/session/addBalance", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "cryptoIdBought": parseInt(cryptoAddId), "amountBought": parseFloat(amountAdd)}),
        });

        if (!response.ok) {
            const errorMessage = await response.json();// Or response.json() if the server returns JSON
            showToast(`Error: ${errorMessage.message}`);
        } else {
            setTimeout(function(){
                window.location.reload()
            },1500)
            showToast(`Success, your coins have been added`);
        }
    } catch (error) {
        showToast(`Error: ${error.message || "An unexpected error occurred."}`);
    }
}

function showToast (message){
    const liveToast = document.getElementById('liveToast')
    const toastBody = liveToast.querySelector(".toast-body");
    toastBody.innerHTML=message
    const toastBootstrap = new bootstrap.Toast(liveToast);
    toastBootstrap.show()
}

/**
 * CHART
 *
 * Utils for displaying chart, author: Łukasz Bilski https://github.com/bilusss
 *
 */
// Candle configurate
function getPriceFormat(){
    return { type: 'price', precision: calculatePrecision(tick), minMove: parseFloat(tick) };
}
function calculatePrecision(tickSize) {
    if (!tickSize.includes('.')) {
        return 0;
    }
    const decimalPart = tickSize.split('.')[1] || "";
    const trimmedDecimalPart = decimalPart.replace(/0+$/, ''); // Ignoring 0 at the end "0.01000000"
    return trimmedDecimalPart.length;
}

function updateTickerDisplay(value) {
    tickerValueElement.textContent = ` (${value}%)`;
    tickerValueElement.classList.remove('green', 'gray', 'red');

    if (value > 0) {
        tickerValueElement.classList.add('green');
        tickerValueElement.textContent = ` (+${value}%)`;
    } else if (value == 0 || value == '0.00') {
        tickerValueElement.classList.add('gray');
    } else {
        tickerValueElement.classList.add('red');
    }
}

// https://github.com/tradingview/lightweight-charts
// https://tradingview.github.io/lightweight-charts/tutorials
function vh(percent) {
    var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
    return (percent * h) / 100;
}

function vw(percent) {
    var w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
    return (percent * w) / 100;
}

function vmin(percent) {
    return Math.min(vh(percent), vw(percent));
}

function vmax(percent) {
    return Math.max(vh(percent), vw(percent));
}

async function populateCryptoDatalist() {
    const cryptos = await getCrypto();
    addDatalist("datalistAdd", cryptos);
    addDatalist("datalistBuy", cryptos);
}

document.addEventListener("DOMContentLoaded", populateCryptoDatalist);