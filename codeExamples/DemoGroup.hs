groups:: Eq t => [t] -> [[t]]
groups [] = []
groups (x:xs) = let (h, r) = break (/=x) xs in (x:h):groups r