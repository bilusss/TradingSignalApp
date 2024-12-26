async function getCrypto() {
    const url = "/api/crypto";
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        console.error(error.message);
    }
}