splitWhen pred [] = ([], [])
splitWhen pred (x:xs)
    |pred x = ([], (x:xs))
    |otherwise = let (ys, zs) = splitWhen pred xs in (x:ys, zs)