//SPDX-License-Identifier: MIT
pragma solidity ^0.8.11;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";

contract NFTMarketplace {

    enum ListingStatus {
        Inactive,
        Active,
        Sold,
        Cancelled
    }

    struct Listing {
        ListingStatus status;
        address seller;
        address token;
        uint tokenId;
        uint price;
    }

    event Listed(
        uint listingId,
        address seller,
        address token,
        uint tokenId,
        uint price
    );

    event Sale(
        uint listingId,
        address buyer,
        address token,
        uint tokenId,
        uint price
    );

    event Cancel(
        uint listingId,
        address seller
    );

    uint private _listingId = 0;
    mapping(uint => Listing) private _listings;

    function listToken(address token, uint tokenId, uint price) public {

        ERC721(token).transferFrom(msg.sender, address(this), tokenId);

        Listing memory listing = Listing(
            ListingStatus.Active,
            msg.sender,
            token,
            tokenId,
            price
        );

        _listings[_listingId] = listing;
        _listingId++;
        emit Listed(
            _listingId,
            msg.sender,
            token,
            tokenId,
            price * 10 ** 18
        );
    }

    function getStatus(uint listingId) public view returns (ListingStatus) {
        Listing storage listing = _listings[listingId];
        return listing.status;
    }

        function getListing(uint listingId) public view returns (Listing memory) {
            return _listings[listingId];
        }


    function getTokenPrice(uint256 listingId) public view returns (uint){
        Listing storage listing = _listings[listingId];
        return listing.price;
    }

    function getIndex() public view returns (uint) {
        return _listingId;
    }

    function buyToken(uint listingId) public payable {
        Listing storage listing = _listings[listingId];

        require(msg.sender != listing.seller, "Seller cannot be buyer");
        require(listing.status == ListingStatus.Active, "Listing is not active");
        require(msg.value >= listing.price, "Insufficient payment");

        listing.status = ListingStatus.Sold;

        ERC721(listing.token).transferFrom(address(this), msg.sender, listing.tokenId);
        payable(listing.seller).transfer(listing.price);

        emit Sale(
            listingId,
            msg.sender,
            listing.token,
            listing.tokenId,
            listing.price
        );
    }

    function cancel(uint listingId) public {
        Listing storage listing = _listings[listingId];

        require(msg.sender == listing.seller, "Only seller can cancel listing");
        require(listing.status == ListingStatus.Active, "Listing is not active");

        listing.status = ListingStatus.Cancelled;

        ERC721(listing.token).transferFrom(address(this), msg.sender, listing.tokenId);

        emit Cancel(listingId, listing.seller);
    }
}