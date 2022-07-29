//SPDX-License-Identifier: MIT
pragma solidity 0.8.11;
pragma experimental ABIEncoderV2;
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";

//https://ipfs.io/ipfs/bafkreiheyjisbbzgavag2ghfph3ru2egvwr36eqgde3vmrjjlekicinyy4?filename=retriver.json
//https://ipfs.io/ipfs/bafkreibse5olb6ho2pdzijcxz2qkvqndswq4nwd7wzisc24hpoue5pgr5y?filename=haski.json
//https://ipfs.io/ipfs/bafkreihjvcl4ebi6gyhfxwwh37mihdeyt2w3oejwg5pxq3wxzx2fjmmdaa?filename=rot.json
//https://ipfs.io/ipfs/bafkreid2naqfxrh3las2a6vheidvzau2vwytxrf3nxpqnmiysyb2fhbqbu?filename=pug.json

contract PKCoin is ERC721URIStorage{
    uint256 public tokenCounter;
    address public owner;
    string[] public dogs;
    mapping(string => bool) dogExist;


    constructor ()public ERC721 ("Kuce", "PAS") {
        owner = msg.sender;
        tokenCounter = 0;
    }

    function createCollectible(string memory tokenURI) external returns (uint256) {
        require(!dogExist[tokenURI]);
        dogs.push(tokenURI);
        uint _id = dogs.length - 1;
        //   uint256 newItemId = tokenCounter;
        _safeMint(msg.sender, _id);
        _setTokenURI(_id, tokenURI);
        //tokenCounter = tokenCounter + 1;
        return _id;
    }


    function getItems() external view returns(string[] memory) {
        return dogs;
    }

    function setTokenURI(uint256 tokenId, string memory _tokenURI) external {
        require(
            _isApprovedOrOwner(_msgSender(), tokenId),
            "ERC721: transfer caller is not owner nor approved"
        );
        _setTokenURI(tokenId, _tokenURI);

    }
}