(ns christmas.day20c-test
 (:require [christmas.day20c :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]
  		   [clojure.zip :as zip]))
(comment
(defn straighten-up [text]
	(str/replace text #"[\^\$]" ""))


(def file (straighten-up (slurp "resources/day16-20/Input20real")))

(def ex-3 (straighten-up "^WNE$"))
(def ex-10 (straighten-up "^ENWWW(NEEE|SSE(EE|N))$"))
(def ex-18 (straighten-up "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"))
(def ex-18b (straighten-up "^ENNWSWW(NEWS|)S(SS|W)EEN(WNSE|)EE(SWWWWWEEEEEN|)NNN$"))
(def ex-23 (straighten-up "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$"))
(def ex-31 (straighten-up "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$"))

(expect "WNE" ex-3)


(expect ["WNE"] (chr/group-input ex-3))
(expect ["ENWWW" "(" "NEEE" "|" "SSE" "(" "EE" "|" "N" "))"] 
	(chr/group-input ex-10))

(expect {[0 0] 0 [1 0] 1 [1 -1] 2 [1 -2] 3 [0 -2] 4 [0 -1] 5 [-1 -1] 6 [-2 -1] 7 } (chr/walk-directions {[0 0] 0} [0 0] "ENNWSWW"))
(expect {[0 0] 0 [-1 0] 1 [-1 1] 2 [-1 2] 3 [-1 3] 4} (chr/walk-directions {[0 0] 0} [0 0] "WSSSNNNE"))
(expect {[0 0] 0 [-1 0] 1 [-1 1] 2 [-1 2] 3 [-1 3] 2} (chr/walk-directions {[-1 3] 2 [0 0] 0} [0 0] "WSSSNNNE"))
(expect {[0 0] 0 [-1 0] 1 [-1 1] 2 [-1 2] 3 [-1 3] 4} (chr/walk-directions {[-1 3] 6 [0 0] 0} [0 0] "WSSSNNNE"))

(expect [0 0] (chr/get-origin "NNWSSE"))
(expect [5 -5] (chr/get-origin "NENNEEEENN"))

(def step-0 {:idx 0 :ways {} :all-dist {[0 0] 0} :position "" :alternatives {}})
(def step-1 {:idx 0, :ways {}, :position "ESSWNN", :alternatives {} :all-dist {[0 0] 0, [1 0] 1, [1 1] 2, [1 2] 3, [0 2] 4, [0 1] 5}})
(def step-2 {:idx 1, :ways {1 "ESSWNN"}, :position "ESSWNN", :alternatives {1 []} :all-dist {[0 0] 0, [1 0] 1, [1 1] 2, [1 2] 3, [0 2] 4, [0 1] 5}})
(def step-3 {:idx 1, :ways {1 "ESSWNN"}, :position "ESSWNNE", :alternatives {1 []} :all-dist {[0 0] 0, [1 0] 1, [1 1] 2, [1 2] 3, [0 2] 4, [0 1] 5}})
(def step-4 {:idx 1, :ways {1 "ESSWNN"}, :position "ESSWNN", :alternatives {1 ["ESSWNNE"]} :all-dist {[0 0] 0, [1 0] 1, [1 1] 2, [1 2] 3, [0 2] 4, [0 1] 5}})

(expect step-1 (chr/tick step-0 "ESSWNN"))
(expect step-2 (chr/tick step-1 "("))
(expect step-3 (chr/tick step-2 "E"))
(expect step-4 (chr/tick step-3 "|"))

(def step-1-18 {:idx 0, :ways {}, :position "ENNWSWW", :alternatives {} :all-dist {[0 0] 0, [1 0] 1, [1 -1] 2, [1 -2] 3, [0 -2] 4, [0 -1] 5, [-1 -1] 6, [-2 -1] 7}})
(def step-2-18 {:idx 1, :ways {1 "ENNWSWW"}, :position "ENNWSWW", :alternatives {1 []} :all-dist {[0 0] 0, [1 0] 1, [1 -1] 2, [1 -2] 3, [0 -2] 4, [0 -1] 5, [-1 -1] 6, [-2 -1] 7}})
(def step-3-18 {:idx 1, :ways {1 "ENNWSWW"}, :position "ENNWSWWNEWS", :alternatives {1 []} :all-dist {[0 0] 0, [1 0] 1, [1 -1] 2, [1 -2] 3, [0 -2] 4, [0 -1] 5, [-1 -1] 6, [-2 -1] 7, [-2 -2] 8, [-1 -2] 9}})
(def step-4-18 {:idx 1, :ways {1 "ENNWSWW"}, :position "ENNWSWW", :alternatives {1 ["ENNWSWWNEWS"]} :all-dist {[0 0] 0, [1 0] 1, [1 -1] 2, [1 -2] 3, [0 -2] 4, [0 -1] 5, [-1 -1] 6, [-2 -1] 7, [-2 -2] 8, [-1 -2] 9}})
(def step-5-18 {:idx 0, :ways {}, :position "ENNWSWWNEWS", :alternatives {} :all-dist {[0 0] 0, [1 0] 1, [1 -1] 2, [1 -2] 3, [0 -2] 4, [0 -1] 5, [-1 -1] 6, [-2 -1] 7, [-2 -2] 8, [-1 -2] 9}})


(expect step-1-18 (chr/tick step-0 "ENNWSWW"))
(expect step-2-18 (chr/tick step-1-18 "("))
(expect step-3-18 (chr/tick step-2-18 "NEWS"))
(expect step-4-18 (chr/tick step-3-18 "|"))
(expect step-5-18 (chr/tick step-4-18 ")"))

(expect 31 (chr/day20 ex-31))
(expect 18 (chr/day20 ex-18))
(expect 10 (chr/day20 ex-10))
(expect 23 (chr/day20 ex-23))
(expect 4778 (chr/day20 file))
(expect 8459 (chr/day20b file))
)


