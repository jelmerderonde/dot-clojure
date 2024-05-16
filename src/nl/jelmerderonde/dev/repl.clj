(ns nl.jelmerderonde.dev.repl)

(defn- start-repl
  [] 
  (let [nrepl (requiring-resolve 'nrepl.cmdline/-main)
        portal-submit (requiring-resolve 'portal.api/submit)
        submit (fn [value] (if (-> value meta :portal.nrepl/eval)
                             (let [{:keys [stdio report result]} value]
                               (when stdio (portal-submit stdio))
                               (when report (portal-submit report))
                               (portal-submit result))
                             (portal-submit value)))]
    (add-tap submit)
    (nrepl "--middleware" "[portal.nrepl/wrap-portal,cider.nrepl/cider-middleware]")
    (System/exit 0)))

