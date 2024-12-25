package com.signalapp.tradingsignalapp.Crypto;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.signalapp.tradingsignalapp.Service.BinanceExchangeInfo;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@DependsOn("binanceExchangeInfo")
public class Crypto {
    public static final Logger log = LoggerFactory.getLogger(CryptoRepository.class);
    private final BinanceExchangeInfo binanceExchangeInfo;
    public Map<Integer, CurrencyDetails> cryptoMap;
    private Integer id; // 1
    private String symbol; // BTC
    private String name; // Bitcoin
    private String description; //for now this will if it's crypto or fiat,
    //[not efficient at all as names are not accurate] later maybe scrape this:https://coinmarketcap.com/currencies/floki-inu/
    private String logoUrl;
    // https://bin.bnbstatic.com/static/assets/logos/BTC.png

    public class CurrencyDetails {
        private String symbol;
        private String name;
        private String description;
        private String logoUrl;

        // Constructor
        public CurrencyDetails(String symbol, String name, String description, String logoUrl) {
            this.symbol = symbol;
            this.name = name;
            this.description = description;
            this.logoUrl = logoUrl;
        }
        // Getters
        public String getSymbol() {
            return symbol;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String getLogoUrl() {
            return logoUrl;
        }
    }

    @PostConstruct
    public void init() {
        creatingCurrencyMap();
    }
    public Crypto(BinanceExchangeInfo binanceExchangeInfo) {
        this.binanceExchangeInfo = binanceExchangeInfo;
    }

    private ArrayList<String> creatingSymbols() {
        if (binanceExchangeInfo == null || binanceExchangeInfo.getSymbolInfoMap() == null) {
            throw new IllegalStateException("BinanceExchangeInfo is not properly initialized!");
        }

        Map<String, BinanceExchangeInfo.SymbolInfo> symbolMap = binanceExchangeInfo.getSymbolInfoMap();
        ArrayList<String> currencyShortNames = new ArrayList<>();

        for (BinanceExchangeInfo.SymbolInfo symbolInfo : symbolMap.values()) {
            currencyShortNames.add(symbolInfo.getBaseAsset());
            currencyShortNames.add(symbolInfo.getQuoteAsset());
        }

        return new ArrayList<>(new HashSet<>(currencyShortNames)); // Only unique currencies
    }
    private ArrayList<Integer> creatingIDs(ArrayList<String> shortnames){ // first index == 1
        return IntStream.rangeClosed(1, shortnames.size())
                .boxed() // Converting int from stream to Integer class obj
                .collect(Collectors.toCollection(ArrayList::new)); // Collecting final stream into new ArrayList
    }
    private ArrayList<String> creatingNames(ArrayList<String> symbols){
        ArrayList<String> currencyNames = new ArrayList<>(Arrays.asList("Adventure Gold", "STP Network", "Scorum Coins", "renBTC", "NEAR Protocol", "Audius", "Bitcoin Diamond", "BitConnect", "Bitcoin Cash", "JUST", "Bytecoin", "Sei", "Jito Staked Solana", "Gravity", "Threshold Network Token", "Wrapped SOL", "SafePal", "CloakCoin", "Jupiter", "Neblio", "Bella Protocol", "Juventus Fan Token", "Baby Doge Coin", "Strike", "EOSDOWN", "Secret", "SKALE", "SXPDOWN", "Skycoin", "SelfSell", "Smooth Love Potion", "Bluzelle", "SONM", "Status", "Synthetix", "API3", "Solana", "Binance Coin", "Celer Network", "TomoChain", "Celo", "Bancor Network Token", "BinaryX", "Botswap", "Serum", "Tornado Cash", "ETHBEAR", "Voyager Token", "Bread", "SSV Network", "Brazilian Real", "Stargate Finance", "IoTeX", "Kadena", "Frontier", "Stacks", "MOBOX", "Substratum", "IOSToken", "Sui", "Biswap", "Sun Token", "Bitcoin", "IOTA", "Bitcoin Gold", "SelfKey", "BitShares", "BitTorrent", "LINKDOWN", "AppCoins", "SXP", "Coin98", "Synapse", "Syscoin", "EOSUP", "AAVEDOWN", "Bitcoin Cash ABC", "Nexo", "SUSHIDOWN", "Komodo", "Kyber Network Crystal", "SingularDTV", "Lamden", "Tether", "StableUSD", "Pax Dollar", "Keep Network", "Lumia", "USD Coin", "TokenClub", "Kusama", "Gala", "Blox", "Synthetix USD", "Velodrome Finance", "Bitcoin BEP2", "RAMP", "VanRy Finance", "The Protocol", "Binance KRW", "Conflux Network", "Celestia", "StormX", "Storj", "SuperRare", "Chromia", "Chiliz", "Ocean Protocol", "Tokocrypto", "UNIDOWN", "DFI.Money", "Alien Worlds", "Nervos Network", "Bitcoin Cash ABC", "Time New Bank", "Clover Finance", "Tierion", "Lazio Fan Token", "CyberMiles", "Toncoin", "Cindicator", "Voxies", "BNBUP", "Colombian Peso", "Contentos", "SUSHIUP", "CoW Protocol", "Tellor", "Ordinals", "TrueFi", "DREP", "Turkish Lira", "TRON", "Orca", "TerraClassicUSD", "Curve DAO Token", "Lido DAO", "CertiK", "Polygon", "TVK", "BlockMason Credit Protocol", "Trust Wallet Token", "Civic", "PowerPool", "1000Cats", "Convex Finance", "SushiSwap", "BitTorrent Chain", "Binance Smart Chain Solana", "Litentry", "Czech Koruna", "Euro Coin", "Liquity", "Compound", "Penguin Karts", "Alpine F1 Team Fan Token", "LeverFi", "Ukrainian Hryvnia", "Neiro Network", "BTCUP", "Livepeer", "Dai", "Mines of Dalarnia", "ETHBULL", "Loopring", "Alpaca Finance", "Dash", "Bitcoin Standard Hashrate Token", "Lisk", "Decred", "Streamr DATAcoin", "Filecoin UP", "Litecoin", "LTO Network", "Linear Finance", "Chainlink", "Filecoin DOWN", "UniLend", "Terra Luna", "COTI", "Aion", "DigiByte", "DigixDAO", "Wing Finance", "Aeternity", "ChatGPT AI Token", "Arweave", "BB Gaming Token", "DIA", "Ardor", "Blur", "dForce", "YOYOW", "BCHDOWN", "FLOKI", "UMA", "EasyFi", "Tael", "Agrello", "Stellar UP", "Uniswap", "BTCDOWN", "GoChain", "Hcash", "district0x", "3x Long Bitcoin Token", "Space ID", "Ionomy", "Polkadot", "Everipedia", "Maverick Protocol", "BeamX", "Arkham", "TRXDOWN", "Pendle", "QuickSwap", "MovieBloc", "LTC DOWN", "TerraClassicUSD", "Crypto.com", "Bounce", "Manta Network", "Cetus Protocol", "Moeda Loyalty Points", "UTrust", "Hegic", "Merit Circle", "MeVerse", "Measurable Data Token", "Mdex", "XRP DOWN", "Binance Wrapped DOT", "NuCypher", "OG Fan Token", "Mainframe", "MANTRA", "Optimism", "Binance USD", "ARPA Chain", "BENQI", "Mirror Protocol", "Siacoin", "dYdX", "Maker", "Keep3rV1", "Enzyme", "Pepe", "Osmosis", "PERL.eco", "Aave", "Perpetual Protocol", "MobileCoin", "Modum", "ConstitutionDAO", "VAI", "ZeroKnowledge Token", "USUAL", "ChatCoin", "WAX", "VeChain", "Monetha", "VeChain", "Metal", "Swerve", "Eidoo", "Open Campus", "3x Short Bitcoin Token", "Beam", "LTC UP", "Voyager", "Viberate", "Viacoin", "Vice Industry Token", "Mexican Peso", "ADADOWN", "Spartan Protocol", "Pundi X NEM", "IRISnet", "Trigger", "aelf", "Bonfida", "Ethernity Cloud", "Enigma", "Enjin Coin", "Ethereum Name Service", "Troy", "EOSIO", "Nebulas", "Hooked Protocol", "NAV Coin", "Ellipsis", "Ellipsis X", "Meme.com", "Binance Vietnamese Dong", "New BitShares", "Tranchess", "Elrond", "Peanut", "Ethernity Chain", "Moonbeam", "Ethereum Classic", "Ethereum", "NEO", "NonFungible Pepe", "Euro", "Nigerian Naira", "Spell Token", "Everex", "Binance Beacon ETH", "Beta Finance", "Binance ETH BEAR", "Wrapped Beacon ETH", "Astar", "NKN", "Wrapped Bitcoin", "Firo", "DOT UP", "Numeraire", "Polymesh", "StratisX", "Notional Finance", "Stratis", "Wanchain", "Stellar DOWN", "AAVEUP", "THORChain", "ListaCoin", "Radiant Capital", "Fetch.ai", "Wi-Fi Coin", "WINkLink", "Nexus", "Marlin", "Polkastarter", "Filecoin", "Polymath", "FIO Protocol", "Stafi", "Akropolis", "Worldcoin", "Harvest Finance", "Flamingo", "WOO Network", "Waves", "WePower", "ForTube", "openANX", "WazirX", "Ethereum DOWN", "Waltonchain", "Tezos DOWN", "Turbo Token", "Unifi Protocol DAO", "Fantom", "PowerLedger", "FTX Token", "Cosmos", "FunFair", "Portal", "Manchester City Fan Token", "Origin Protocol", "EOS BEAR", "Aergo", "MultiversX", "Pundi X", "Frax Share", "Polkadot DOWN", "Alchemix", "Shiba Inu", "Viberate", "PancakeSwap", "OMG Network", "Reef", "Harmony", "Ontology Gas", "Algorand", "Ontology", "Side AI", "Dusk Network", "NANO", "VIDT DAO", "Project Galaxy", "Gas", "Bome", "Orion Protocol", "British Pound", "BONK", "1INCH UP", "BarnBridge", "SXP UP", "Simple Token", "eCash", "NEM", "CATI Token", "Binance GBP", "Gifto", "Orchid", "BNBBULL", "Tribe", "1000 Satoshi", "Stellar", "SuperFarm", "Render Token", "MoversCoin", "Moonriver", "Monero", "Golem", "Nano", "STEPN", "GMX", "Omni", "Gnosis", "Vite", "Golem Network Token", "Gains Network", "Paxos Standard", "XRP", "The Graph", "Groestlcoin", "Theta Fuel", "Pando Protocol", "KAIA", "Terra Classic", "Terra", "Tezos", "EOS BULL", "Gitcoin", "BurgerCities", "Gifto", "Verge", "Venus", "Dego Finance", "Genesis Vision", "Phala Network", "Red Pulse Phoenix", "XRP UP", "Red Pulse Phoenix", "TRON UP", "Zcoin", "Highstreet", "GXChain", "Hifi Finance", "Auto", "Nirvana Chain", "PlayDapp", "Polish Zloty", "Magic Internet Money", "pNetwork", "Dent", "Pyth Network", "Poa.Network", "Poet", "Polkastream", "Populous", "Kava", "Paris Saint-Germain Fan Token", "Cocos-BCX", "yearn.finance", "Binance KRW Stablecoin", "Yield Guild Games", "Hashflow", "Ampleforth Governance Token", "IDEX", "DeXe", "Avalanche", "Banana Token", "Hive", "Vulcan Forged PYR", "SagaToken", "MetisDAO", "Qtum", "1INCH DOWN", "Helium", "Ethereum Fair", "Holo", "Cover Protocol", "SALT", "The Sandbox", "Hshare", "TrueUSD", "Indonesian Rupiah Token", "HARD Protocol", "Aave", "NCash", "Flow", "1inch", "QuarkChain", "Oasis Network", "Badger DAO", "Multichain", "QLC Chain", "Etherparty", "Aevo", "Flux", "LINK UP", "Quant", "Alipay Euro", "South African Rand", "Dock", "Ronin", "Render Token", "Ankr", "Pivx", "Quantstamp", "ICONOMI", "DODO", "Internet Computer", "Zcash", "ICON", "Horizen", "Dogecoin", "Alpha Finance Lab", "Hedera Hashgraph", "DogSwap", "Cartesi", "Neutron", "DeCombo", "THETA", "XRP BEAR", "yearn.finance DOWN", "Biconomy", "Hamster", "Zilliqa", "Cortex", "Tezos UP", "Binance IDR", "Ooki Protocol", "Prosper", "Prometeus", "Beefy.Finance", "FC Porto Fan Token", "Illuvium", "Cream Finance", "Immutable", "UNI UP", "Injective", "InsureAce", "EigenLayer", "Radicle", "Raydium", "First Digital USD", "Zero Knowledge RO", "0x", "Santos FC Fan Token", "EasyFi", "Wrapped NXM", "ADA UP", "Ripio Credit Network", "Raiden Network Token", "Acala", "AceD Coin", "Alchemy Pay", "Mina Protocol", "AC Milan Fan Token", "VeThor Token", "Achain", "Access Protocol", "REI Network", "Ren", "Augur", "Cardano", "Request", "bZx Protocol", "PAX Gold", "REZI", "ADX", "Jasmy", "Rigel Protocol", "Augur", "Reflexer", "Steem", "XRP Bull", "Mithril", "TUSD", "Wings", "iExec RLC", "Klaytn", "Altcoin", "Ambrosus", "Amp", "NULS", "Ronin", "Anchor Protocol", "Aragon", "BakerySwap", "AnySwap", "Rocket Pool", "Alice", "RPX", "ApeCoin", "Aptos", "ETH Bull", "Band Protocol", "Reserve", "Arbitrum", "Loka", "Ark", "Aragon Network", "Ars", "Russian Ruble", "AS Roma Fan Token", "Astor", "Decentraland", "Atari", "ATM", "TNSR", "Loom Network", "Ravencoin", "Australian Dollar", "Avalanche", "Bitcoin Cash Up", "Mask Network", "Pixel", "Axelar", "Axie Infinity", "Bitcoin Cash SV", "Ghost", "Binance Down", "Yearn Finance Up", "Joe", "dYdX", "AGI Token", "Japanese Yen", "CyberConnect", "StormX", "Balancer", "BarnBridge", "Basic Attention Token"));
        //TODO Find better way to provide names
        return currencyNames;
    }
    private ArrayList<String> getFiatSymbols() {
        ArrayList<String> fiatSymbols = new ArrayList<>(Arrays.asList("JPY", "EUR", "USD", "BRL", "TRY", "UAH", "CZK", "MXN", "GBP", "NGN", "ZAR", "AUD", "ARS", "RUB"));
        return fiatSymbols;
    }
    private ArrayList<String> creatingDescriptions(ArrayList<String> allSymbols) {
        ArrayList<String> fiatSymbols = getFiatSymbols();
        ArrayList<String> descriptions = new ArrayList<>();
        for (String symbol : allSymbols) {
            if (!fiatSymbols.contains(symbol)) {
                descriptions.add("Cryptocurrency");
            }else{
                descriptions.add("Fiat Currency");
            }
        }
        return descriptions;
    }
    private ArrayList<String> creatingLogoUrls(ArrayList<String> symbols) {
        ArrayList<String> urls = new ArrayList<>();
        String url_base = "https://bin.bnbstatic.com/static/assets/logos/";
        String url_ending = ".png";
        for (String symbol : symbols) {
            urls.add(url_base + symbol + url_ending);
        }
        return urls;
    }

    public void creatingCurrencyMap() {
        log.info("Generating currency map...");
        ArrayList<String> symbols = creatingSymbols();
        ArrayList<Integer> ids = creatingIDs(symbols);
        ArrayList<String> names = creatingNames(symbols);
        ArrayList<String> descriptions = creatingDescriptions(symbols);
        ArrayList<String> logoUrls = creatingLogoUrls(symbols);

        cryptoMap = new HashMap<>();

        for (int i = 0; i < symbols.size(); i++) {
            try {
                CurrencyDetails details = new CurrencyDetails(
                        symbols.get(i),
                        names.get(i),
                        descriptions.get(i),
                        logoUrls.get(i)
                );
                cryptoMap.put(ids.get(i), details);
            } catch (Exception e) {
                log.error("Error creating currency details for index {}: ", i, e);
            }
        }
        log.info("Currency map generated with {} entries.", cryptoMap.size());
    }
}
