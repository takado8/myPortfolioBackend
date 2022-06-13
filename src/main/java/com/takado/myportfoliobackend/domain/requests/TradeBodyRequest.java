package com.takado.myportfoliobackend.domain.requests;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.TradeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeBodyRequest extends BodyRequest {
    private TradeDto tradeDto;

    public TradeBodyRequest(TradeDto tradeDto, DigitalSignature digitalSignature){
        super(digitalSignature);
        this.tradeDto = tradeDto;
    }
}
