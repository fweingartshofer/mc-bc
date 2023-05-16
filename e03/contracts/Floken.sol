// SPDX-License-Identifier:  AFL-3.0
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/utils/math/SafeMath.sol";


contract Floken is ERC20 {
    using SafeMath for uint256;
    uint public INITIAL_SUPPLY = 12000;

    constructor () ERC20("Floken", "Flo") {
        _mint( msg.sender, INITIAL_SUPPLY );
    }

    function decimals() pure public override returns (uint8) {
        return 2;
    }
}