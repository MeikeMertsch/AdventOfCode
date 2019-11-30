(ns christmas.chr16.day09-test
  (:require [christmas.chr16.day09 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day09real"))

(def test-6 "ADVENT")
(def test-7 "A(1x5)BC") ;ABBBBBC
(def test-9 "(3x3)XYZ") ;XYZXYZXYZ
(def test-11 "A(2x2)BCD(2x2)EFG") ;ABCBCDEFEFG
(def test-6-2 "(6x1)(1x3)A") ;(1x3)A
(def test-18 "X(8x2)(3x3)ABCY") ;X(3x3)ABC(3x3)ABCY

;(expect ["C" 6] (chr/take-next [test-7 0]))
;(expect ["A" [1 5] "BC"] (chr/split-next 6 test-7))
;(expect ["A" [1 5]] (chr/chop-front "A(1x5)"))
;(expect ["A" [1 5]] (chr/chop-all ["A" "1x5)"]))
;(expect [1 5] (chr/create-instructions "1x5)"))

;(expect 6 (chr/day09 test-6))
;(expect 7 (chr/day09 test-7))
;(expect 9 (chr/day09 test-9))
;(expect 11 (chr/day09 test-11))
;(expect 6 (chr/day09 test-6-2))
;(expect 18 (chr/day09 test-18))
;(expect 70186 (chr/day09 file))

(def test-b-9 "(3x3)XYZ") ;XYZXYZXYZ
(def test-b-20 "X(8x2)(3x3)ABCY") ;XABCABCABCABCABCABCY
(def test-b-241920 "(27x12)(20x12)(13x14)(7x10)(1x12)A") ;A repeated 241920 times.
(def test-b-445 "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") ;becomes 445 characters long.

;(expect ["X" "(8x2)(3x3)ABC" "Y"] (chr/break-up test-b-20))

;(expect 9 (chr/day09b test-b-9))
;(expect 20 (chr/day09b test-b-20))
;(expect 241920 (chr/day09b test-b-241920))
;(expect 445 (chr/day09b test-b-445))
