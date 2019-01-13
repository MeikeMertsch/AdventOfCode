(ns christmas.day11-test
 (:require [christmas.day11 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(comment
(expect 4 (chr/power [3 5] 8))
(expect -5 (chr/power [122 79] 57))
(expect 0 (chr/power [217 196] 39))
(expect 4 (chr/power [101 153] 71))

(expect 9 (chr/digit 949))
(expect 0 (chr/digit 56))

;(expect #{[1 1][1 2][2 1][2 2]} (into #{} (chr/sqare-fn vector 1 2)))

;(expect -5 (get (chr/powered-board 57) [122 79]))

;(expect 29 (chr/total-power [33 45] 18 3 (chr/powered-board 18)))

(expect [4 4 4] (take 3 (drop 32 (chr/powered-row 45 18))))
(expect [[4 4 4][3 3 4][1 2 4]]  (map #(take 3 (drop 32 %)) (take 3 (drop 44 (chr/powered-board 18)))))


;(expect 29 (chr/square-power [33 45] 3 (chr/powered-board 18)))
;(expect [29 3 33 45] (chr/size-max 3 (chr/powered-board 18)))

;(expect "" (chr/calculate-board 9306 3))
;(expect-focused "" (chr/top 9306))
; Ran 1 tests containing 1 assertions in 25989786 msecs

;(expect-focused [3 [33 45]] (chr/largest-cell 18 3))
;;;;  (expect [3 [235 38]] (chr/largest-cell 9306 3))

;(expect-focused [113 [90 269] 16] (chr/top-2 18))


;(expect-focused "" (chr/all-total-powers 18 3))
;(expect-focused "" (chr/top-3 9306)) ; for first 50 in 22058 msecs
; 146710 msecs for 51 to 75
;[66 9 [241 149]] 2 10 in 30s
;[95 13 [233 146]] in 1.5h

;(expect-focused [[241 149] 9 66] (chr/top-2 9306 2 10)) ; 21859 msecs
;(expect-focused [[233 146] 13 95] (chr/top-2 9306 11 20)) ; 114548 msecs
;(expect-focused [[234 138] 21 63] (chr/top-2 9306 21 30)) ; 278034 msecs
;(expect-focused [[241 140] 31 -132] (chr/top-2 9306 31 40)) ; 570673 msecs



;(expect [12 [232 251]] (chr/top 42))

;(expect-focused "" (map #(chr/largest-cell 9306 %) (range 10 100)))



;----------------


;(expect 0 (get (chr/powered-board 39) [217 196]))
;(expect 4 (get (chr/powered-board 71) [101 153]))

;(expect-focused [3 [33 45]] (chr/largest-cell-2 18 3))

)