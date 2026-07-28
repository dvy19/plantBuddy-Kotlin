package com.example.plantbuddy.FAQRetrofit

 data class FaqRequest (
     var question_id:Int,
     var plant_id:Int
 )

data class FaqResponse(
    var cache: Boolean,
    var data:FaqData

)

data class FaqData(
    var plant_name:String,
    var question_title:String,
    var answer:String,
    var updated_at:String
)

/*
{
    "cached": true,
    "data": {
        "plant_name": "Boston Fern",
        "question_title": "Give me 5 Facts",
        "answer": "1. The Boston Fern, scientifically known as Nephrolepis exaltata, is a classic houseplant famous for its beautiful, feathery fronds. 2. It belongs to the air-purifying plants category, meaning it helps clean the indoor air you breathe. 3. This plant is a perennial with a moderate growth rate, and it can grow to an average height of 90.0 centimeters. 4. In its natural habitat, it thrives in high humidity environments and requires a high humidity level of 75.0. 5. As a resilient tropical plant, it prefers temperatures between 16°C and 26°C and grows best in a peat or sphagnum soil mix.",
        "updated_at": "2026-07-28T15:04:49.349408Z"
    }
}
 */