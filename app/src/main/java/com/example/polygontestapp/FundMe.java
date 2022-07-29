package com.example.polygontestapp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
 * <p>Generated with web3j version 3.6.0.
 */
public class FundMe extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610df2806100616000396000f3fe60806040526004361061007b5760003560e01c80638da5cb5b1161004e5780638da5cb5b1461012f57806398d5fdca1461015a578063b60d428814610185578063dc0d3dff1461018f5761007b565b80630d8e6e2c146100805780630fdb1c10146100ab57806355f2a7d5146100b55780636e5b6b28146100f2575b600080fd5b34801561008c57600080fd5b506100956101cc565b6040516100a29190610773565b60405180910390f35b6100b361025b565b005b3480156100c157600080fd5b506100dc60048036038101906100d791906107f1565b610440565b6040516100e99190610773565b60405180910390f35b3480156100fe57600080fd5b506101196004803603810190610114919061084a565b610458565b6040516101269190610773565b60405180910390f35b34801561013b57600080fd5b50610144610492565b6040516101519190610886565b60405180910390f35b34801561016657600080fd5b5061016f6104b8565b60405161017c9190610773565b60405180910390f35b61018d610561565b005b34801561019b57600080fd5b506101b660048036038101906101b1919061084a565b610674565b6040516101c39190610886565b60405180910390f35b60008073d0d5e3db44de05e9f294bb0a3beeaf030de24ada90508073ffffffffffffffffffffffffffffffffffffffff166354fd4d506040518163ffffffff1660e01b8152600401602060405180830381865afa158015610231573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061025591906108b6565b91505090565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146102eb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102e290610940565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc479081150290604051600060405180830381858888f19350505050158015610331573d6000803e3d6000fd5b5060005b6001805490508110156103dd5760006001828154811061035857610357610960565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505080806103d5906109be565b915050610335565b50600067ffffffffffffffff8111156103f9576103f8610a07565b5b6040519080825280602002602001820160405280156104275781602001602082028036833780820191505090505b506001908051906020019061043d9291906106b3565b50565b60006020528060005260406000206000915090505481565b6000806104636104b8565b90506000670de0b6b3a7640000848361047c9190610a36565b6104869190610abf565b90508092505050919050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008073d0d5e3db44de05e9f294bb0a3beeaf030de24ada905060008173ffffffffffffffffffffffffffffffffffffffff1663feaf968c6040518163ffffffff1660e01b815260040160a060405180830381865afa15801561051f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105439190610b68565b5050509150506402540be4008161055a9190610be3565b9250505090565b6000674563918244f4000090508061057834610458565b10156105b9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105b090610d46565b60405180910390fd5b346000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282546106079190610d66565b925050819055506001339080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6001818154811061068457600080fd5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b82805482825590600052602060002090810192821561072c579160200282015b8281111561072b5782518260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550916020019190600101906106d3565b5b509050610739919061073d565b5090565b5b8082111561075657600081600090555060010161073e565b5090565b6000819050919050565b61076d8161075a565b82525050565b60006020820190506107886000830184610764565b92915050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006107be82610793565b9050919050565b6107ce816107b3565b81146107d957600080fd5b50565b6000813590506107eb816107c5565b92915050565b6000602082840312156108075761080661078e565b5b6000610815848285016107dc565b91505092915050565b6108278161075a565b811461083257600080fd5b50565b6000813590506108448161081e565b92915050565b6000602082840312156108605761085f61078e565b5b600061086e84828501610835565b91505092915050565b610880816107b3565b82525050565b600060208201905061089b6000830184610877565b92915050565b6000815190506108b08161081e565b92915050565b6000602082840312156108cc576108cb61078e565b5b60006108da848285016108a1565b91505092915050565b600082825260208201905092915050565b7f506f67726573616e204b6f7269736e696b000000000000000000000000000000600082015250565b600061092a6011836108e3565b9150610935826108f4565b602082019050919050565b600060208201905081810360008301526109598161091d565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006109c98261075a565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156109fc576109fb61098f565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000610a418261075a565b9150610a4c8361075a565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0483118215151615610a8557610a8461098f565b5b828202905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b6000610aca8261075a565b9150610ad58361075a565b925082610ae557610ae4610a90565b5b828204905092915050565b600069ffffffffffffffffffff82169050919050565b610b0f81610af0565b8114610b1a57600080fd5b50565b600081519050610b2c81610b06565b92915050565b6000819050919050565b610b4581610b32565b8114610b5057600080fd5b50565b600081519050610b6281610b3c565b92915050565b600080600080600060a08688031215610b8457610b8361078e565b5b6000610b9288828901610b1d565b9550506020610ba388828901610b53565b9450506040610bb4888289016108a1565b9350506060610bc5888289016108a1565b9250506080610bd688828901610b1d565b9150509295509295909350565b6000610bee82610b32565b9150610bf983610b32565b9250827f7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0482116000841360008413161615610c3857610c3761098f565b5b817f80000000000000000000000000000000000000000000000000000000000000000583126000841260008413161615610c7557610c7461098f565b5b827f80000000000000000000000000000000000000000000000000000000000000000582126000841360008412161615610cb257610cb161098f565b5b827f7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0582126000841260008412161615610cef57610cee61098f565b5b828202905092915050565b7f4e65646f766f6c6a6e6f204d4154494320636f696e6121000000000000000000600082015250565b6000610d306017836108e3565b9150610d3b82610cfa565b602082019050919050565b60006020820190508181036000830152610d5f81610d23565b9050919050565b6000610d718261075a565b9150610d7c8361075a565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115610db157610db061098f565b5b82820190509291505056fea26469706673582212204c4b740f40cf2486d4e0ea47ca977db001944b19cba7884587be79c47db0265764736f6c634300080b0033";

    public static final String FUNC_ADRESSTOAMOUNTFUNDED = "adressToAmountFunded";

    public static final String FUNC_FUNDERS = "funders";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_FUND = "fund";

    public static final String FUNC_GETVERSION = "getVersion";

    public static final String FUNC_GETPRICE = "getPrice";

    public static final String FUNC_GETCONVERSIONRATE = "getConversionRate";

    public static final String FUNC_WITHDRAW = "withDraw";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("80001", "0x74d6b56ABd568164aa099ac942e88143Dbdd17f5");
    }

    @Deprecated
    protected FundMe(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FundMe(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FundMe(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FundMe(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FundMe> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FundMe.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<FundMe> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FundMe.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<FundMe> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FundMe.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<FundMe> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FundMe.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<BigInteger> adressToAmountFunded(String param0) {
        final Function function = new Function(FUNC_ADRESSTOAMOUNTFUNDED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> funders(BigInteger param0) {
        final Function function = new Function(FUNC_FUNDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> fund(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_FUND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getVersion() {
        final Function function = new Function(FUNC_GETVERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getPrice() {
        final Function function = new Function(FUNC_GETPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getConversionRate(BigInteger ethAmount) {
        final Function function = new Function(FUNC_GETCONVERSIONRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ethAmount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> withDraw(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    @Deprecated
    public static FundMe load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FundMe(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FundMe load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FundMe(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FundMe load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FundMe(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FundMe load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FundMe(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
