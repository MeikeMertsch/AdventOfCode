(ns christmas.day09-test
 (:require [christmas.day09 :as chr]
 		 	[expectations :refer :all]))

(expect [{} [[0 4 2] '(5 1 3)]] (chr/next-circle 9 5 [{} [[0] '(4 2 1 3)]]))
(expect [{} [[0 4 2 5 1 6 3] '(7)]] (chr/next-circle 8 7 [{} [[0 4 2 5 1] '(6 3)]]))
(expect [{} [[0] '(4 2 1 3)]] (chr/next-circle 2 4 [{} [[0 2 1] '(3)]]))
(expect [{1 12 5 32} [[0 16 8 17 4 18] '(19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15)]]
	(chr/next-circle 9 23 [{1 12} [[0 16 8 17 4 18 9 19 2 20 10 21 5] '(22 11 1 12 6 13 3 14 7 15)]]))
(expect [{5 32} [[0 16 8 17 4 18] '(19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15)]]
	(chr/next-circle 9 23 [{} [[0 16 8 17 4 18 9 19 2 20 10 21 5]  '(22 11 1 12 6 13 3 14 7 15)]]))

(expect [{} [[0] '(2 1)]] (chr/do-all-moves 2 2))

(expect 32 (chr/day09 9 25))

;(expect 146373 (chr/day09 13 7999))
;(expect 8317 (chr/day09 10 1618))
;(expect 2764 (chr/day09 17 1104))
;(expect 54718 (chr/day09 21 6111))
;(expect 37305 (chr/day09 30 5807))

;(expect-focused 374287 (chr/day09 468 (* 100 71010)))
;(expect-focused 374287 (chr/day09 468 71010)) ; in ~ 40k msecs

(expect [{} [[0] '(4 2 1 3)]] (chr/insert-a-marble {} 4 [[0 2 1] '(3)]))
(expect [{} [[0 4 2] '(5 1 3)]] (chr/insert-a-marble {} 5 [[0] '(4 2 1 3)]))

(expect [{1 32} [[0 16 8 17 4 18] '(19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15)]] 
	(chr/calc-points {} 1 23 [[0 16 8 17 4 18 9 19 2 20 10 21 5] '(22 11 1 12 6 13 3 14 7 15)]))

(expect [{1 30} [[0 16 8 1 2 3 4 5 6] '(8 9 15)]] 
	(chr/calc-points {} 1 23 [[0 16 8] '(1 2 3 4 5 6 7 8 9 15)]))