package com.example.polygontestapp;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class NFTMarketPlace extends Contract {
    public static final String BINARY = "0x60806040526000805534801561001457600080fd5b5061114f806100246000396000f3fe6080604052600436106100705760003560e01c80635c622a0e1161004e5780635c622a0e146100f75780637e07590d1461013457806381045ead1461015d578063c457fb371461018857610070565b8063107a274a146100755780632d296bf1146100b257806340e58ee5146100ce575b600080fd5b34801561008157600080fd5b5061009c60048036038101906100979190610b7a565b6101c5565b6040516100a99190610cd6565b60405180910390f35b6100cc60048036038101906100c79190610b7a565b6102ed565b005b3480156100da57600080fd5b506100f560048036038101906100f09190610b7a565b6105f8565b005b34801561010357600080fd5b5061011e60048036038101906101199190610b7a565b61083e565b60405161012b9190610d00565b60405180910390f35b34801561014057600080fd5b5061015b60048036038101906101569190610d47565b610870565b005b34801561016957600080fd5b50610172610aa4565b60405161017f9190610da9565b60405180910390f35b34801561019457600080fd5b506101af60048036038101906101aa9190610b7a565b610aad565b6040516101bc9190610da9565b60405180910390f35b6101cd610ad2565b600160008381526020019081526020016000206040518060a00160405290816000820160009054906101000a900460ff1660038111156102105761020f610ba7565b5b600381111561022257610221610ba7565b5b81526020016000820160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600282015481526020016003820154815250509050919050565b60006001600083815260200190815260200160002090508060000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610397576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038e90610e21565b60405180910390fd5b600160038111156103ab576103aa610ba7565b5b8160000160009054906101000a900460ff1660038111156103cf576103ce610ba7565b5b1461040f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161040690610e8d565b60405180910390fd5b8060030154341015610456576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161044d90610ef9565b60405180910390fd5b60028160000160006101000a81548160ff0219169083600381111561047e5761047d610ba7565b5b02179055508060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd303384600201546040518463ffffffff1660e01b81526004016104e893929190610f28565b600060405180830381600087803b15801561050257600080fd5b505af1158015610516573d6000803e3d6000fd5b505050508060000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc82600301549081150290604051600060405180830381858888f19350505050158015610588573d6000803e3d6000fd5b507ff3debaf22196a6ddf52a60fa71058cd7edf5e25bb98f4e96c994a5d4a9b0dd3e82338360010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16846002015485600301546040516105ec959493929190610f5f565b60405180910390a15050565b60006001600083815260200190815260200160002090508060000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146106a1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069890610ffe565b60405180910390fd5b600160038111156106b5576106b4610ba7565b5b8160000160009054906101000a900460ff1660038111156106d9576106d8610ba7565b5b14610719576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161071090610e8d565b60405180910390fd5b60038160000160006101000a81548160ff0219169083600381111561074157610740610ba7565b5b02179055508060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd303384600201546040518463ffffffff1660e01b81526004016107ab93929190610f28565b600060405180830381600087803b1580156107c557600080fd5b505af11580156107d9573d6000803e3d6000fd5b505050507f8dd3c361eb2366ff27c2db0eb07b9261f1d052570742ab8c9a0c326f37aa576d828260000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660405161083292919061101e565b60405180910390a15050565b6000806001600084815260200190815260200160002090508060000160009054906101000a900460ff16915050919050565b8273ffffffffffffffffffffffffffffffffffffffff166323b872dd3330856040518463ffffffff1660e01b81526004016108ad93929190610f28565b600060405180830381600087803b1580156108c757600080fd5b505af11580156108db573d6000803e3d6000fd5b5050505060006040518060a0016040528060016003811115610900576108ff610ba7565b5b81526020013373ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff16815260200184815260200183815250905080600160008054815260200190815260200160002060008201518160000160006101000a81548160ff0219169083600381111561098957610988610ba7565b5b021790555060208201518160000160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506060820151816002015560808201518160030155905050600080815480929190610a4590611076565b91905055507f723f73331eaee88eec7fc68ef60ab6ed15e4b90d0472b55eb92fa43910bab6dd600054338686670de0b6b3a764000087610a8591906110bf565b604051610a96959493929190610f5f565b60405180910390a150505050565b60008054905090565b6000806001600084815260200190815260200160002090508060030154915050919050565b6040518060a0016040528060006003811115610af157610af0610ba7565b5b8152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600081525090565b600080fd5b6000819050919050565b610b5781610b44565b8114610b6257600080fd5b50565b600081359050610b7481610b4e565b92915050565b600060208284031215610b9057610b8f610b3f565b5b6000610b9e84828501610b65565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b60048110610be757610be6610ba7565b5b50565b6000819050610bf882610bd6565b919050565b6000610c0882610bea565b9050919050565b610c1881610bfd565b82525050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610c4982610c1e565b9050919050565b610c5981610c3e565b82525050565b610c6881610b44565b82525050565b60a082016000820151610c846000850182610c0f565b506020820151610c976020850182610c50565b506040820151610caa6040850182610c50565b506060820151610cbd6060850182610c5f565b506080820151610cd06080850182610c5f565b50505050565b600060a082019050610ceb6000830184610c6e565b92915050565b610cfa81610bfd565b82525050565b6000602082019050610d156000830184610cf1565b92915050565b610d2481610c3e565b8114610d2f57600080fd5b50565b600081359050610d4181610d1b565b92915050565b600080600060608486031215610d6057610d5f610b3f565b5b6000610d6e86828701610d32565b9350506020610d7f86828701610b65565b9250506040610d9086828701610b65565b9150509250925092565b610da381610b44565b82525050565b6000602082019050610dbe6000830184610d9a565b92915050565b600082825260208201905092915050565b7f53656c6c65722063616e6e6f7420626520627579657200000000000000000000600082015250565b6000610e0b601683610dc4565b9150610e1682610dd5565b602082019050919050565b60006020820190508181036000830152610e3a81610dfe565b9050919050565b7f4c697374696e67206973206e6f74206163746976650000000000000000000000600082015250565b6000610e77601583610dc4565b9150610e8282610e41565b602082019050919050565b60006020820190508181036000830152610ea681610e6a565b9050919050565b7f496e73756666696369656e74207061796d656e74000000000000000000000000600082015250565b6000610ee3601483610dc4565b9150610eee82610ead565b602082019050919050565b60006020820190508181036000830152610f1281610ed6565b9050919050565b610f2281610c3e565b82525050565b6000606082019050610f3d6000830186610f19565b610f4a6020830185610f19565b610f576040830184610d9a565b949350505050565b600060a082019050610f746000830188610d9a565b610f816020830187610f19565b610f8e6040830186610f19565b610f9b6060830185610d9a565b610fa86080830184610d9a565b9695505050505050565b7f4f6e6c792073656c6c65722063616e2063616e63656c206c697374696e670000600082015250565b6000610fe8601e83610dc4565b9150610ff382610fb2565b602082019050919050565b6000602082019050818103600083015261101781610fdb565b9050919050565b60006040820190506110336000830185610d9a565b6110406020830184610f19565b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061108182610b44565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156110b4576110b3611047565b5b600182019050919050565b60006110ca82610b44565b91506110d583610b44565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff048311821515161561110e5761110d611047565b5b82820290509291505056fea2646970667358221220b2e198d4b6a38aadbf4f95934b128f64dec85470ff6afa49f80f81cfe8b6749b64736f6c634300080b0033";

    public static final String FUNC_LISTTOKEN = "listToken";

    public static final String FUNC_GETSTATUS = "getStatus";

    public static final String FUNC_GETLISTING = "getListing";

    public static final String FUNC_GETTOKENPRICE = "getTokenPrice";

    public static final String FUNC_GETINDEX = "getIndex";

    public static final String FUNC_BUYTOKEN = "buyToken";

    public static final String FUNC_CANCEL = "cancel";

    public static final Event CANCEL_EVENT = new Event("Cancel", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event LISTED_EVENT = new Event("Listed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SALE_EVENT = new Event("Sale", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("80001", "0xbd575085E9536D5AAd101bde040a061f511E4830");
    }

    @Deprecated
    protected NFTMarketPlace(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFTMarketPlace(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFTMarketPlace(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFTMarketPlace(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CancelEventResponse> getCancelEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CANCEL_EVENT, transactionReceipt);
        ArrayList<CancelEventResponse> responses = new ArrayList<CancelEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelEventResponse typedResponse = new CancelEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CancelEventResponse>() {
            @Override
            public CancelEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCEL_EVENT, log);
                CancelEventResponse typedResponse = new CancelEventResponse();
                typedResponse.log = log;
                typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.seller = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCEL_EVENT));
        return cancelEventFlowable(filter);
    }

    public List<ListedEventResponse> getListedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LISTED_EVENT, transactionReceipt);
        ArrayList<ListedEventResponse> responses = new ArrayList<ListedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ListedEventResponse typedResponse = new ListedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ListedEventResponse> listedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ListedEventResponse>() {
            @Override
            public ListedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LISTED_EVENT, log);
                ListedEventResponse typedResponse = new ListedEventResponse();
                typedResponse.log = log;
                typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.seller = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.token = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ListedEventResponse> listedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LISTED_EVENT));
        return listedEventFlowable(filter);
    }

    public List<SaleEventResponse> getSaleEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SALE_EVENT, transactionReceipt);
        ArrayList<SaleEventResponse> responses = new ArrayList<SaleEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SaleEventResponse typedResponse = new SaleEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SaleEventResponse> saleEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SaleEventResponse>() {
            @Override
            public SaleEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SALE_EVENT, log);
                SaleEventResponse typedResponse = new SaleEventResponse();
                typedResponse.log = log;
                typedResponse.listingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.token = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SaleEventResponse> saleEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SALE_EVENT));
        return saleEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> listToken(String token, BigInteger tokenId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LISTTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(token), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getStatus(BigInteger listingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Listing> getListing(BigInteger listingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETLISTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Listing>() {}));
        return executeRemoteCallSingleValueReturn(function, Listing.class);
    }

    public RemoteFunctionCall<BigInteger> getTokenPrice(BigInteger listingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getIndex() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETINDEX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyToken(BigInteger listingId, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel(BigInteger listingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static NFTMarketPlace load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTMarketPlace(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFTMarketPlace load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTMarketPlace(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFTMarketPlace load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NFTMarketPlace(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFTMarketPlace load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFTMarketPlace(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFTMarketPlace> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NFTMarketPlace.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NFTMarketPlace> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NFTMarketPlace.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<NFTMarketPlace> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NFTMarketPlace.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NFTMarketPlace> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NFTMarketPlace.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class Listing extends StaticStruct {
        public BigInteger status;

        public String seller;

        public String token;

        public BigInteger tokenId;

        public BigInteger price;

        public Listing(BigInteger status, String seller, String token, BigInteger tokenId, BigInteger price) {
            super(new org.web3j.abi.datatypes.generated.Uint8(status),new org.web3j.abi.datatypes.Address(seller),new org.web3j.abi.datatypes.Address(token),new org.web3j.abi.datatypes.generated.Uint256(tokenId),new org.web3j.abi.datatypes.generated.Uint256(price));
            this.status = status;
            this.seller = seller;
            this.token = token;
            this.tokenId = tokenId;
            this.price = price;
        }

        public Listing(Uint8 status, Address seller, Address token, Uint256 tokenId, Uint256 price) {
            super(status,seller,token,tokenId,price);
            this.status = status.getValue();
            this.seller = seller.getValue();
            this.token = token.getValue();
            this.tokenId = tokenId.getValue();
            this.price = price.getValue();
        }
    }

    public static class CancelEventResponse extends BaseEventResponse {
        public BigInteger listingId;

        public String seller;
    }

    public static class ListedEventResponse extends BaseEventResponse {
        public BigInteger listingId;

        public String seller;

        public String token;

        public BigInteger tokenId;

        public BigInteger price;
    }

    public static class SaleEventResponse extends BaseEventResponse {
        public BigInteger listingId;

        public String buyer;

        public String token;

        public BigInteger tokenId;

        public BigInteger price;
    }
}
