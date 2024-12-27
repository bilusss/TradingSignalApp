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